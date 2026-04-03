package com.yazzzeh.ms.machineavailability_service.repository;

import com.yazzzeh.ms.machineavailability_service.model.MachineAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MachineAvailabilityRepository extends JpaRepository<MachineAvailability, Long> {

    Optional<MachineAvailability> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumberAndStatus(String serialNumber, String status);

    List<MachineAvailability> findByStatus(String status);

    List<MachineAvailability> findByStatusAndCertified(String status, Boolean certified);
}
