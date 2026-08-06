package com.sentinelvault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.SharedFile;
import com.sentinelvault.entity.User;

public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {

    // All files shared with a user
    List<SharedFile> findBySharedWith(User sharedWith);

    // All shares created by an owner
    List<SharedFile> findByOwner(User owner);

    // Check if a file is already shared with a user
    boolean existsByFileAndSharedWith(FileEntity file, User sharedWith);

    // Delete a specific shared file
    void deleteByFileAndSharedWith(FileEntity file, User sharedWith);
}