package com.yazzzeh.ms.machine.controller;

import com.yazzzeh.ms.machine.dtos.MachineRequest;
import com.yazzzeh.ms.machine.dtos.MachineResponse;
import com.yazzzeh.ms.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machine")
public class MachineController {

    @Autowired
    private MachineService machineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MachineResponse registerMachine(@RequestBody MachineRequest machineRequest){
        return machineService.registerMachine(machineRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MachineResponse> getAllMachines(){
        return machineService.getAllMachines();
    }

    @DeleteMapping("/{id}")
    String deleteMachineById(@PathVariable String id){
        machineService.deleteMachineById(id);
        return "the machine is deleted successfully";
    }
}
