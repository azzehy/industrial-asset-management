package com.yazzzeh.ms.machineavailability_service.controller;

import com.yazzzeh.ms.machineavailability_service.model.MachineAvailability;
import com.yazzzeh.ms.machineavailability_service.service.MachineAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machine-availability")
public class MachineAvailabilityController {

    @Autowired
    private MachineAvailabilityService machineAvailabilityService;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isMachineAvailable(@RequestParam String serialNumber) {
        return machineAvailabilityService.isMachineAvailable(serialNumber);
    }

    @GetMapping("/available-machines")
    public List<MachineAvailability> getAllAvailabeMachines(){
        return machineAvailabilityService.getAllAvailabeMachines();
    }
    @GetMapping("/available-certified")
    public List<MachineAvailability> getAllAvailableAndCertifiedMachines(){
        return machineAvailabilityService.getAllAvailableAndCertifiedMachines();
    }
    @GetMapping("/")
    public List<MachineAvailability> getAll(){
        return machineAvailabilityService.getAll();
    }
}
