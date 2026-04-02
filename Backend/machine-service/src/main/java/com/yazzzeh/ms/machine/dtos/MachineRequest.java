package com.yazzzeh.ms.machine.dtos;

import java.time.LocalDate;

public record MachineRequest(
        String serialNumber,
        String model,
        String manufacturer,
        String type,
        String vesselOrLocation,
        LocalDate installationDate
) {
}
