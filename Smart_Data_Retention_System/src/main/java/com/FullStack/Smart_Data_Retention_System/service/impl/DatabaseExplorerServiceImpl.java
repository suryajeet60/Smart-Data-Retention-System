package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.dto.TableMetadataDTO;
import com.FullStack.Smart_Data_Retention_System.exception.SmartDataRetentionException;
import com.FullStack.Smart_Data_Retention_System.service.DatabaseExplorerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DatabaseExplorerServiceImpl implements DatabaseExplorerService
{

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getAllTables()
    {

        List<String> tables = new ArrayList<>();

        try (Connection connection = dataSource.getConnection())
        {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet rs = metaData.getTables(
                    connection.getCatalog(),
                    null,
                    "%",
                    new String[]{"TABLE"});

            while (rs.next())
            {

                String table = rs.getString("TABLE_NAME");

                if (!table.equalsIgnoreCase("flyway_schema_history"))
                {
                    tables.add(table);
                }

            }

        }
        catch (Exception ex)
        {
            throw new SmartDataRetentionException("Unable to read database tables.", ex);
        }
        return tables;
    }

    @Override
    public TableMetadataDTO getTableMetadata(String tableName)
    {

        if (!tableExists(tableName))
        {
            throw new SmartDataRetentionException("Table not found : " + tableName);
        }

        return TableMetadataDTO.builder()
                .tableName(tableName)
                .columns(getColumns(tableName))
                .dateColumns(getDateColumns(tableName))
                .primaryKeyColumn(getPrimaryKeyColumn(tableName))
                .totalRecords(getTotalRecords(tableName))
                .build();
    }

    @Override
    public List<String> getColumns(String tableName)
    {

        List<String> columns = new ArrayList<>();

        try (Connection connection = dataSource.getConnection())
        {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet rs = metaData.getColumns(
                    connection.getCatalog(),
                    null,
                    tableName,
                    "%");

            while (rs.next())
            {
                columns.add(rs.getString("COLUMN_NAME"));
            }

        }
        catch (Exception ex)
        {
            throw new SmartDataRetentionException("Unable to fetch columns.", ex);
        }
        return columns;
    }

    @Override
    public List<String> getDateColumns(String tableName)
    {

        List<String> dateColumns = new ArrayList<>();

        try (Connection connection = dataSource.getConnection())
        {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet rs = metaData.getColumns(
                    connection.getCatalog(),
                    null,
                    tableName,
                    "%");

            while (rs.next())
            {

                int type = rs.getInt("DATA_TYPE");

                if (type == Types.DATE || type == Types.TIME || type == Types.TIMESTAMP || type == Types.TIMESTAMP_WITH_TIMEZONE)
                {
                    dateColumns.add(rs.getString("COLUMN_NAME"));
                }
            }
        }
        catch (Exception ex)
        {
            throw new SmartDataRetentionException("Unable to fetch date columns.", ex);
        }
        return dateColumns;
    }

    @Override
    public String getPrimaryKeyColumn(String tableName)
    {

        try (Connection connection = dataSource.getConnection())
        {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet rs = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);

            if (rs.next())
            {
                return rs.getString("COLUMN_NAME");
            }

        }
        catch (Exception ex)
        {
            throw new SmartDataRetentionException("Unable to fetch primary key.", ex);
        }

        return null;
    }

    @Override
    public Long getTotalRecords(String tableName)
    {

        String sql = "SELECT COUNT(*) FROM " + tableName;

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count == null ? 0L : count;
    }

    @Override
    public boolean tableExists(String tableName)
    {

        return getAllTables()
                .stream()
                .anyMatch(table ->
                        table.equalsIgnoreCase(tableName));
    }
}
