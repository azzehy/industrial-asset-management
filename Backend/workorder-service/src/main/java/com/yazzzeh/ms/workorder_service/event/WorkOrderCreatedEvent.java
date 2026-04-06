package com.yazzzeh.ms.workorder_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderCreatedEvent {
    private String workOrderNumber;
    private String technicianEmail;
}
