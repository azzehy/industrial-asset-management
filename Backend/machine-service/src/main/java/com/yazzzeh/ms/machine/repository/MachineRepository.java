package com.yazzzeh.ms.machine.repository;

import com.yazzzeh.ms.machine.model.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MachineRepository extends MongoRepository<Machine, String> {
}
