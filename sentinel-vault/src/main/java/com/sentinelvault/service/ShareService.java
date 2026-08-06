package com.sentinelvault.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.ShareLink;
import com.sentinelvault.repository.FileRepository;
import com.sentinelvault.repository.ShareRepository;

@Service
public class ShareService {

    private final ShareRepository shareRepository;
    private final FileRepository fileRepository;

    public ShareService(
            ShareRepository shareRepository,
            FileRepository fileRepository) {

        this.shareRepository = shareRepository;
        this.fileRepository = fileRepository;
    }

    // Create Share Link
    public String createShareLink(Long fileId) {

        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));

        ShareLink shareLink = new ShareLink();

        shareLink.setFile(file);

        shareLink.setShareToken(
                UUID.randomUUID().toString());

        shareLink.setCreatedAt(
                LocalDateTime.now());

        shareLink.setExpiryTime(
                LocalDateTime.now().plusHours(24));

        shareRepository.save(shareLink);

        return shareLink.getShareToken();
    }

    // Validate Share Link
    public ShareLink validateShareLink(String token) {

        ShareLink shareLink =
                shareRepository.findByShareToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid share link"));

        if (shareLink.getExpiryTime().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Share link has expired");

        }

        return shareLink;
    }

}