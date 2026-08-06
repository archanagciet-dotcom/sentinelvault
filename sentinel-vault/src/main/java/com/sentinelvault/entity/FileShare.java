package com.sentinelvault.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "file_shares")
public class FileShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // File being shared
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    // User who shared the file
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // User receiving the file
    @ManyToOne
    @JoinColumn(name = "shared_with_id")
    private User sharedWith;

    public FileShare() {
    }

    public FileShare(FileEntity file, User owner, User sharedWith) {
        this.file = file;
        this.owner = owner;
        this.sharedWith = sharedWith;
    }

    public Long getId() {
        return id;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(User sharedWith) {
        this.sharedWith = sharedWith;
    }
}