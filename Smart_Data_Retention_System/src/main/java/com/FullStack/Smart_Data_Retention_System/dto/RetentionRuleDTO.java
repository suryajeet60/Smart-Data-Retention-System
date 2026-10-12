package com.FullStack.Smart_Data_Retention_System.dto;

import com.FullStack.Smart_Data_Retention_System.entity.type.RetentionActionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetentionRuleDTO
{

    private Long id;

    @NotBlank(message = "Table name is required.")
    private String tableName;

    @NotBlank(message = "Date column is required.")
    private String dateColumn;

    @NotNull(message = "Retention period is required.")
    @Min(value = 1, message = "Retention period must be greater than 0.")
    private Integer retentionPeriodDays;

    @NotNull(message = "Retention action is required.")
    private RetentionActionType action;

    private Boolean active;

    private LocalDateTime createdAt;

    // Information about creator
    private Long createdById;

    private String createdByName;
}
