package com.yazzzeh.ms.machineavailability_service.service;

import com.yazzzeh.ms.machineavailability_service.model.MachineAvailability;
import com.yazzzeh.ms.machineavailability_service.repository.MachineAvailabilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MachineAvailabilityService {

    @Autowired
    private MachineAvailabilityRepository machineAvailabilityRepository;

    public boolean isMachineAvailable(String serialNumber){

        log.info("Checking availability for machine {}", serialNumber);

        Optional<MachineAvailability> availability = machineAvailabilityRepository
                .findBySerialNumber(serialNumber);

        if (availability.isEmpty()){
            return false;
        }

        MachineAvailability machine = availability.get();

        boolean available =
                "AVAILABLE".equals(machine.getStatus())
                && Boolean.TRUE.equals(machine.getCertified());

        log.info("Machine {} availability result: {}", serialNumber, available);

        return available;
    }

    public List<MachineAvailability> getAllAvailabeMachines(){
        return machineAvailabilityRepository.findByStatus("AVAILABLE");
    }

    public List<MachineAvailability> getAllAvailableAndCertifiedMachines(){
        return machineAvailabilityRepository.findByStatusAndCertified("AVAILABLE", true);
    }

    public List<MachineAvailability> getAll(){
        return machineAvailabilityRepository.findAll();
    }
}
