package com.sentinelvault.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;
import com.sentinelvault.service.FileService;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {

    private final FileService fileService;
    private final UserRepository userRepository;

    public FileController(
            FileService fileService,
            UserRepository userRepository) {

        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    // ==========================
    // Upload File
    // ==========================

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            Authentication authentication)
            throws IOException {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        FileEntity savedFile =
                fileService.saveFile(file, user.getId());

        return ResponseEntity.ok(
                "File uploaded successfully : "
                        + savedFile.getFileName());
    }

    // ==========================
    // My Files
    // ==========================

    @GetMapping("/my-files")
    public ResponseEntity<List<FileEntity>> getMyFiles(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return ResponseEntity.ok(
                fileService.getFilesByUser(user.getId()));
    }

    // ==========================
    // Download File
    // ==========================

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        FileEntity file =
                fileService.downloadFile(id, user.getId());

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(file.getFileType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                file.getFileName() + "\"")
                .body(file.getFileData());
    }

    // ==========================
    // Preview File
    // ==========================

    @GetMapping("/preview/{id}")
    public ResponseEntity<byte[]> previewFile(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        FileEntity file =
                fileService.previewFile(id, user.getId());

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(file.getFileType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                file.getFileName() + "\"")
                .body(file.getFileData());
    }

    // ==========================
    // Delete File
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        fileService.deleteFileByUser(id, user.getId());

        return ResponseEntity.ok("File deleted successfully");
    }

}