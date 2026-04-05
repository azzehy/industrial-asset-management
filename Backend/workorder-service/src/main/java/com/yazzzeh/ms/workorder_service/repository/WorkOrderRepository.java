package com.yazzzeh.ms.workorder_service.repository;

import com.yazzzeh.ms.workorder_service.model.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
}
