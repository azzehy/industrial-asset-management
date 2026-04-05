package com.yazzzeh.ms.workorder_service.dtos;

import java.time.LocalDate;

public record WorkOrderRequest(
        String serialNumber,
        String type,
        LocalDate scheduledDate,
        TechnicianDetails technicianDetails
) {
    public record TechnicianDetails(String email, String firstName, String lastName) {}
}