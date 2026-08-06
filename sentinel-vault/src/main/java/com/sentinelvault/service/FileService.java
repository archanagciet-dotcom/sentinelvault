package com.sentinelvault.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.User;
import com.sentinelvault.repository.FileRepository;
import com.sentinelvault.repository.UserRepository;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public FileService(
            FileRepository fileRepository,
            UserRepository userRepository,
            AuditLogService auditLogService) {

        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    // Upload File
    public FileEntity saveFile(
            MultipartFile file,
            Long userId) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        FileEntity fileEntity = new FileEntity();

        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setUploadDate(LocalDateTime.now());
        fileEntity.setFileData(file.getBytes());
        fileEntity.setUser(user);

        FileEntity savedFile = fileRepository.save(fileEntity);

        auditLogService.logAction(
                user.getEmail(),
                "UPLOAD",
                savedFile.getFileName());

        return savedFile;
    }

    // My Files
    public List<FileEntity> getFilesByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return fileRepository.findByUser(user);
    }

    // Find File
    public FileEntity getFileById(Long id) {

        return fileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));
    }

    // Check Ownership
    public FileEntity getFileByIdAndUser(
            Long fileId,
            Long userId) {

        FileEntity file = getFileById(fileId);

        if (!file.getUser().getId().equals(userId)) {

            throw new RuntimeException("Access denied");
        }

        return file;
    }

    // Download
    public FileEntity downloadFile(
            Long fileId,
            Long userId) {

        FileEntity file =
                getFileByIdAndUser(fileId, userId);

        auditLogService.logAction(
                file.getUser().getEmail(),
                "DOWNLOAD",
                file.getFileName());

        return file;
    }

    // Preview
    public FileEntity previewFile(
            Long fileId,
            Long userId) {

        FileEntity file =
                getFileByIdAndUser(fileId, userId);

        auditLogService.logAction(
                file.getUser().getEmail(),
                "PREVIEW",
                file.getFileName());

        return file;
    }

    // Delete
    public void deleteFileByUser(
            Long fileId,
            Long userId) {

        FileEntity file =
                getFileByIdAndUser(fileId, userId);

        auditLogService.logAction(
                file.getUser().getEmail(),
                "DELETE",
                file.getFileName());

        fileRepository.delete(file);
    }
}