package com.sentinelvault.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shared_files")
public class SharedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // File being shared
    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity file;

    // Owner of the file
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // User with whom the file is shared
    @ManyToOne
    @JoinColumn(name = "shared_with_id", nullable = false)
    private User sharedWith;

    public SharedFile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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