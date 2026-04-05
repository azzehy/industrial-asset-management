package com.yazzzeh.ms.workorder_service.controller;

import com.yazzzeh.ms.workorder_service.dtos.WorkOrderRequest;
import com.yazzzeh.ms.workorder_service.model.WorkOrder;
import com.yazzzeh.ms.workorder_service.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workorder")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createWorkOrder(@RequestBody WorkOrderRequest workOrderRequest){
        workOrderService.createWorkOrder(workOrderRequest);
        return "Work Order Placed Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkOrder> getAllWorkOrders(){
        return workOrderService.getAllWorkOrders();
    }
    
    @GetMapping("/{id}")
    public WorkOrder getWorkOrder(@PathVariable Long id){
        return workOrderService.getWorkOrder(id);
    }

    @GetMapping("/test")
    public String catchAll() {
        return "Caught something";
    }
}
