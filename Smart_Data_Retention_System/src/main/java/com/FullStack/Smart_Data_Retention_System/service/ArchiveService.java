package com.FullStack.Smart_Data_Retention_System.service;

import com.FullStack.Smart_Data_Retention_System.entity.RetentionRule;

public interface ArchiveService
{
    Long archiveExpiredRecords(RetentionRule rule);
}
