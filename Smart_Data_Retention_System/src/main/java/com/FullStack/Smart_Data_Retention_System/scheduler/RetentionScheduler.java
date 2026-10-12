package com.FullStack.Smart_Data_Retention_System.scheduler;

import com.FullStack.Smart_Data_Retention_System.dto.RuleExecutionDTO;
import com.FullStack.Smart_Data_Retention_System.service.RetentionExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetentionScheduler
{

    private final RetentionExecutionService retentionExecutionService;

    @Scheduled(cron = "0 0 2 * * *")
    public void executeRetentionRules()
    {

        log.info("=================================================");
        log.info("Retention Scheduler Started");
        log.info("=================================================");

        try
        {

            List<RuleExecutionDTO> executions = retentionExecutionService.executeAllActiveRules();

            log.info("Total Rules Executed : {}", executions.size());

            executions.forEach(result ->
                    log.info(
                            "RuleId={} | Table={} | Action={} | Affected={} | Status={}",
                            result.getRuleId(),
                            result.getTableName(),
                            result.getAction(),
                            result.getAffectedRecords(),
                            result.getStatus()));

            log.info("Retention Scheduler Completed Successfully.");

        }
        catch (Exception ex)
        {
            log.error("Retention Scheduler Failed.", ex);
        }
        log.info("=================================================");
    }
}
