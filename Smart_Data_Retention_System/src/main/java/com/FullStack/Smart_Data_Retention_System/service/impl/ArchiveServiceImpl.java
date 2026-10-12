package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.entity.RetentionRule;
import com.FullStack.Smart_Data_Retention_System.exception.SmartDataRetentionException;
import com.FullStack.Smart_Data_Retention_System.service.ArchiveService;
import com.FullStack.Smart_Data_Retention_System.service.DatabaseExplorerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveServiceImpl implements ArchiveService
{

    private final JdbcTemplate jdbcTemplate;

    private final DatabaseExplorerService databaseExplorerService;

    @Override
    public Long archiveExpiredRecords(RetentionRule rule)
    {

        LocalDateTime expiryDate = LocalDateTime.now().minusDays(rule.getRetentionPeriodDays());

        String sourceTable = rule.getTableName();

        String archiveTable = sourceTable + "_archive";

        createArchiveTableIfNotExists(sourceTable, archiveTable);

        String countSql =
                "SELECT COUNT(*) FROM "
                        + sourceTable
                        + " WHERE "
                        + rule.getDateColumn()
                        + " < ?";

        Long count = jdbcTemplate.queryForObject(countSql, Long.class, Timestamp.valueOf(expiryDate));

        if (count == null || count == 0)
        {
            return 0L;
        }

        String insertSql =
                "INSERT INTO "
                        + archiveTable
                        + " SELECT * FROM "
                        + sourceTable
                        + " WHERE "
                        + rule.getDateColumn()
                        + " < ?";

        jdbcTemplate.update(insertSql, Timestamp.valueOf(expiryDate));

        String deleteSql =
                "DELETE FROM "
                        + sourceTable
                        + " WHERE "
                        + rule.getDateColumn()
                        + " < ?";

        jdbcTemplate.update(deleteSql, Timestamp.valueOf(expiryDate));

        return count;
    }

    private void createArchiveTableIfNotExists(String sourceTable, String archiveTable)
    {

        if (databaseExplorerService.tableExists(archiveTable))
        {
            return;
        }

        try
        {

            String sql =
                    "CREATE TABLE "
                            + archiveTable
                            + " LIKE "
                            + sourceTable;

            jdbcTemplate.execute(sql);

        }
        catch (Exception ex)
        {
            throw new SmartDataRetentionException("Unable to create archive table : " + archiveTable, ex);
        }
    }
}
