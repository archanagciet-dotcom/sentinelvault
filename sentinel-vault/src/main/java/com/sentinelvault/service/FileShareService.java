package com.sentinelvault.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.FileShare;
import com.sentinelvault.entity.User;
import com.sentinelvault.repository.FileRepository;
import com.sentinelvault.repository.FileShareRepository;
import com.sentinelvault.repository.UserRepository;

@Service
public class FileShareService {

    private final FileShareRepository fileShareRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileShareService(
            FileShareRepository fileShareRepository,
            FileRepository fileRepository,
            UserRepository userRepository) {

        this.fileShareRepository = fileShareRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    // Share file with another user
    public String shareFile(Long fileId, String ownerEmail, String receiverEmail) {

        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Owner not found"));

        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() ->
                        new RuntimeException("Receiver not found"));

        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));

        // Verify ownership
        if (!file.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("You can only share your own files");
        }

        // Prevent duplicate sharing
        if (fileShareRepository.existsByFileAndSharedWith(file, receiver)) {
            return "File is already shared with this user";
        }

        FileShare fileShare = new FileShare();
        fileShare.setFile(file);
        fileShare.setOwner(owner);
        fileShare.setSharedWith(receiver);

        fileShareRepository.save(fileShare);

        return "File shared successfully";
    }

    // Files shared with logged-in user
    public List<FileEntity> getSharedFiles(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return fileShareRepository.findBySharedWith(user)
                .stream()
                .map(FileShare::getFile)
                .collect(Collectors.toList());
    }
}