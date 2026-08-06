package com.sentinelvault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.FileShare;
import com.sentinelvault.entity.User;

@Repository
public interface FileShareRepository extends JpaRepository<FileShare, Long> {

    // All files shared with a user
    List<FileShare> findBySharedWith(User sharedWith);

    // All shares created by an owner
    List<FileShare> findByOwner(User owner);

    // Find shares for a particular file
    List<FileShare> findByFile(FileEntity file);

    // Check whether a file is already shared with a user
    boolean existsByFileAndSharedWith(FileEntity file, User sharedWith);
}