package com.FullStack.Smart_Data_Retention_System.controller;

import com.FullStack.Smart_Data_Retention_System.dto.TableMetadataDTO;
import com.FullStack.Smart_Data_Retention_System.service.DatabaseExplorerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/database")
@RequiredArgsConstructor
public class DatabaseExplorerController
{

    private final DatabaseExplorerService databaseExplorerService;

    // Database Tables
    @GetMapping("/tables")
    public List<String> getAllTables()
    {
        return databaseExplorerService.getAllTables();
    }

    // Table Metadata
    @GetMapping("/tables/{tableName}")
    public TableMetadataDTO getTableMetadata(@PathVariable String tableName)
    {
        return databaseExplorerService.getTableMetadata(tableName);
    }

    // Columns
    @GetMapping("/tables/{tableName}/columns")
    public List<String> getColumns(@PathVariable String tableName)
    {
        return databaseExplorerService.getColumns(tableName);
    }

    // Date Columns
    @GetMapping("/tables/{tableName}/date-columns")
    public List<String> getDateColumns(@PathVariable String tableName)
    {
        return databaseExplorerService.getDateColumns(tableName);
    }

    // Primary Key
    @GetMapping("/tables/{tableName}/primary-key")
    public String getPrimaryKey(@PathVariable String tableName)
    {
        return databaseExplorerService.getPrimaryKeyColumn(tableName);
    }

    // Total Records
    @GetMapping("/tables/{tableName}/count")
    public Long getTotalRecords(@PathVariable String tableName)
    {
        return databaseExplorerService.getTotalRecords(tableName);
    }

    // Table Exists
    @GetMapping("/tables/{tableName}/exists")
    public boolean tableExists(@PathVariable String tableName)
    {
        return databaseExplorerService.tableExists(tableName);
    }
}
