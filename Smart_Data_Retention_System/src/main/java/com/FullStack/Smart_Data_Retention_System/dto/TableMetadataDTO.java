package com.FullStack.Smart_Data_Retention_System.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableMetadataDTO
{

    private String tableName;

    private List<String> columns;

    private List<String> dateColumns;

    private String primaryKeyColumn;

    private Long totalRecords;

}
