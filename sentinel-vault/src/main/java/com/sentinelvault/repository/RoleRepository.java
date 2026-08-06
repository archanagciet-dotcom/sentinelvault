package com.sentinelvault.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sentinelvault.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}