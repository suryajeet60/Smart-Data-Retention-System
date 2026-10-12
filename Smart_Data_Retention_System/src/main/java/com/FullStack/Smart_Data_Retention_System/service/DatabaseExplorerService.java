package com.FullStack.Smart_Data_Retention_System.service;

import com.FullStack.Smart_Data_Retention_System.dto.TableMetadataDTO;

import java.util.List;

public interface DatabaseExplorerService
{

    // Database Tables
    List<String> getAllTables();

    TableMetadataDTO getTableMetadata(String tableName);

    List<String> getColumns(String tableName);

    List<String> getDateColumns(String tableName);

    String getPrimaryKeyColumn(String tableName);

    Long getTotalRecords(String tableName);

    boolean tableExists(String tableName);
}
