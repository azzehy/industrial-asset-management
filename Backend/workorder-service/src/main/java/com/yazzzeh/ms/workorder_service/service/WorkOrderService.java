package com.yazzzeh.ms.workorder_service.service;

import com.yazzzeh.ms.workorder_service.client.MachineAvailabilityClient;
import com.yazzzeh.ms.workorder_service.dtos.WorkOrderRequest;
import com.yazzzeh.ms.workorder_service.event.WorkOrderCreatedEvent;
import com.yazzzeh.ms.workorder_service.model.WorkOrder;
import com.yazzzeh.ms.workorder_service.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private MachineAvailabilityClient availabilityClient;
    @Autowired
    private KafkaTemplate<String, WorkOrderCreatedEvent> kafkaTemplate;

    public void createWorkOrder(WorkOrderRequest workOrderRequest){

        boolean isAvailable = availabilityClient.isMachineAvailable(workOrderRequest.serialNumber());

        if(!isAvailable){
            throw new RuntimeException(
                    "Machine " + workOrderRequest.serialNumber() + " is not available for operation"
            );
        }
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderNumber(UUID.randomUUID().toString());
        workOrder.setSerialNumber(workOrderRequest.serialNumber());
        workOrder.setType(workOrderRequest.type());
        workOrder.setStatus("SCHEDULED");
        workOrder.setScheduledDate(workOrderRequest.scheduledDate());

        workOrder.setTechnicianEmail(workOrderRequest.technicianDetails().email());
        workOrder.setTechnicianFirstName(workOrderRequest.technicianDetails().firstName());
        workOrder.setTechnicianLastName(workOrderRequest.technicianDetails().lastName());

        publishEvent(workOrder);

        workOrderRepository.save(workOrder);

        log.info("Work order {} created for machine {}",
                workOrder.getWorkOrderNumber(),
                workOrder.getSerialNumber());


    }

    private void publishEvent(WorkOrder workOrder) {
        WorkOrderCreatedEvent event = new WorkOrderCreatedEvent(
                workOrder.getWorkOrderNumber(),
                workOrder.getTechnicianEmail()
        );
        log.info("Start - Sending WorOrderPlacedEvent {} to Kafka topic workorder-placed", event);
        kafkaTemplate.send("workorder-placed", event);
        log.info("End - Sending WorOrderPlacedEvent {} to Kafka topic workorder-placed", event);

    }

    public List<WorkOrder> getAllWorkOrders(){
        return workOrderRepository.findAll();
    }
    
//    public Optional<WorkOrder> getWorkOrder(Long id){
//        return workOrderRepository.findById(id);
//
//    }

    // Service
    public WorkOrder getWorkOrder(Long id) {
        return workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkOrder not found with id: " + id));
    }
}
