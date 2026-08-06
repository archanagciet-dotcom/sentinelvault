package com.sentinelvault.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "share_links")
public class ShareLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shareToken;

    private LocalDateTime createdAt;

    private LocalDateTime expiryTime;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    public ShareLink() {
    }

    public Long getId() {
        return id;
    }

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}