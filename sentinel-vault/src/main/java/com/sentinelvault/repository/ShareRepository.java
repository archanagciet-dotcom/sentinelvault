package com.sentinelvault.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sentinelvault.entity.ShareLink;

@Repository
public interface ShareRepository extends JpaRepository<ShareLink, Long> {

    Optional<ShareLink> findByShareToken(String shareToken);

}