package com.sentinelvault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sentinelvault.entity.FileEntity;
import com.sentinelvault.entity.User;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    // Get all files of a user
    List<FileEntity> findByUser(User user);

    // Search files by file name
    List<FileEntity> findByUserAndFileNameContainingIgnoreCase(
            User user,
            String fileName
    );

}