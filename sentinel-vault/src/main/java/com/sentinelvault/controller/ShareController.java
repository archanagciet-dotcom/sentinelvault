package com.sentinelvault.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.ShareLink;
import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;
import com.sentinelvault.service.FileService;
import com.sentinelvault.service.ShareService;

@RestController
@RequestMapping("/share")
@CrossOrigin("*")
public class ShareController {

    private final ShareService shareService;
    private final FileService fileService;
    private final UserRepository userRepository;

    public ShareController(
            ShareService shareService,
            FileService fileService,
            UserRepository userRepository) {

        this.shareService = shareService;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    // Generate Share Link
    @PostMapping("/{fileId}")
    public ResponseEntity<String> generateShareLink(
            @PathVariable Long fileId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Verify that the file belongs to the logged-in user
        fileService.getFileByIdAndUser(fileId, user.getId());

        String token = shareService.createShareLink(fileId);

        String link =
                "http://localhost:8082/share/download/" + token;

        return ResponseEntity.ok(link);
    }

    // Download Shared File
    @GetMapping("/download/{token}")
    public ResponseEntity<byte[]> downloadSharedFile(
            @PathVariable String token) {

        ShareLink shareLink =
                shareService.validateShareLink(token);

        FileEntity file = shareLink.getFile();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(file.getFileType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                file.getFileName() + "\"")
                .body(file.getFileData());
    }

}