package com.sentinelvault.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sentinelvault.util.EncryptionUtil;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads";
    private final EncryptionUtil encryptionUtil;

    public FileStorageService(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    // Save encrypted file
    public String saveFile(MultipartFile file) throws IOException {

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Read original file bytes
            byte[] fileBytes = file.getBytes();

            // Encrypt the file
            byte[] encryptedBytes = encryptionUtil.encrypt(fileBytes);

            // Save encrypted file
            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            Files.write(filePath, encryptedBytes);

            return file.getOriginalFilename();

        } catch (Exception e) {
            throw new IOException("Error while encrypting file", e);
        }
    }

    // Get file path
    public Path getFile(String fileName) {

        return Paths.get(uploadDir).resolve(fileName);
    }

    // Read and decrypt file
    public byte[] getDecryptedFile(String fileName) throws IOException {

        try {

            Path filePath = getFile(fileName);

            byte[] encryptedBytes = Files.readAllBytes(filePath);

            return encryptionUtil.decrypt(encryptedBytes);

        } catch (Exception e) {
            throw new IOException("Error while decrypting file", e);
        }
    }

    // Delete file from disk
    public boolean deletePhysicalFile(String fileName) throws IOException {

        Path filePath = getFile(fileName);

        return Files.deleteIfExists(filePath);
    }
}