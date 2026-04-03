package com.yazzzeh.ms.machineavailability_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "t_machine_availability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;      // Link to Machine service

    private String status;            // AVAILABLE, IN_OPERATION, UNDER_MAINTENANCE, OUT_OF_SERVICE

    private String currentLocation;   // Vessel name, dock, offshore platform

    private LocalDate lastInspectionDate;

    private Boolean certified;        // Safety compliance
}