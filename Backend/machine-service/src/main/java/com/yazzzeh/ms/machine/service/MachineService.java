package com.yazzzeh.ms.machine.service;

import com.yazzzeh.ms.machine.dtos.MachineRequest;
import com.yazzzeh.ms.machine.dtos.MachineResponse;
import com.yazzzeh.ms.machine.model.Machine;
import com.yazzzeh.ms.machine.repository.MachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    public MachineResponse registerMachine(MachineRequest request){

        Machine machine = Machine.builder()
                .serialNumber(request.serialNumber())
                .model(request.model())
                .manufacturer(request.manufacturer())
                .type(request.type())
                .vesselOrLocation(request.vesselOrLocation())
                .installationDate(request.installationDate())
                .operationalStatus("ACTIVE")
                .hoursOfOperation(0)
                .build();

        machineRepository.save(machine);

        log.info("Machine registered successfully: {}", machine.getSerialNumber());

        return mapToResponse(machine);
    }

    private MachineResponse mapToResponse(Machine machine) {
        return new MachineResponse(
                machine.getId(),
                machine.getSerialNumber(),
                machine.getModel(),
                machine.getManufacturer(),
                machine.getType(),
                machine.getVesselOrLocation(),
                machine.getInstallationDate(),
                machine.getLastMaintenanceDate(),
                machine.getOperationalStatus(),
                machine.getHoursOfOperation()
        );
    }

    public List<MachineResponse> getAllMachines() {
        return machineRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteMachineById(String id){
        machineRepository.deleteById(id);
    }

}
