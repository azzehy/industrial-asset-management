package com.yazzzeh.ms.machine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "machine")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Machine {
    @Id
    private String id;

    private String serialNumber;          // Unique industrial identifier
    private String model;
    private String manufacturer;

    private String type;                  // e.g. Crane, Engine, Hydraulic Pump
    private String vesselOrLocation;      // Ship name, dock, offshore platform

    private LocalDate installationDate;
    private LocalDate lastMaintenanceDate;

    private String operationalStatus;     // ACTIVE, UNDER_MAINTENANCE, RETIRED
    private Integer hoursOfOperation;
}
