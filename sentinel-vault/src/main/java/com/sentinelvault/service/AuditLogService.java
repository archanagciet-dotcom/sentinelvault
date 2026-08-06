package com.sentinelvault.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sentinelvault.entity.AuditLog;
import com.sentinelvault.repository.AuditLogRepository;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Save an audit log
    public void logAction(String userEmail, String action, String fileName) {

        AuditLog auditLog = new AuditLog();

        auditLog.setUserEmail(userEmail);
        auditLog.setAction(action);
        auditLog.setFileName(fileName);
        auditLog.setActionTime(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    // Get logs for a user
    public List<AuditLog> getUserLogs(String userEmail) {

        return auditLogRepository.findByUserEmailOrderByActionTimeDesc(userEmail);
    }

    // Get all logs
    public List<AuditLog> getAllLogs() {

        return auditLogRepository.findAll();
    }
}