package com.yazzzeh.ms.workorder_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "t_work_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workOrderNumber;

    private String serialNumber;          // Machine identifier

    private String type;                  // INSPECTION, REPAIR, INSTALLATION
    private String status;                // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED

    private LocalDate scheduledDate;
    private LocalDate completionDate;

    private String technicianEmail;
    private String technicianFirstName;
    private String technicianLastName;
}
