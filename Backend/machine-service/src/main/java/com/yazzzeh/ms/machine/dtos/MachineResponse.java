package com.yazzzeh.ms.machine.dtos;

import java.time.LocalDate;

public record MachineResponse(
        String id,
        String serialNumber,
        String model,
        String manufacturer,
        String type,
        String vesselOrLocation,
        LocalDate installationDate,
        LocalDate lastMaintenanceDate,
        String operationalStatus,
        Integer hoursOfOperation
) {
}
