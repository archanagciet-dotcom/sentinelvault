package com.sentinelvault.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sentinelvault.entity.AuditLog;
import com.sentinelvault.service.AuditLogService;

@RestController
@RequestMapping("/audit")
@CrossOrigin("*")
public class AuditController {

    private final AuditLogService auditLogService;

    public AuditController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getLogs(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                auditLogService.getUserLogs(email)
        );
    }
}