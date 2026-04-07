package com.yazzzeh.ms.notification.event;

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
