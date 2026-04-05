package com.yazzzeh.ms.workorder_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface MachineAvailabilityClient{

    Logger log = LoggerFactory.getLogger(MachineAvailabilityClient.class);

    @GetExchange("/api/machine-availability/check")
    @CircuitBreaker(name = "machineavailability", fallbackMethod = "fallbackMethod")
    @Retry(name = "machineavailability")
    boolean isMachineAvailable(@RequestParam String serialNumber);

    default boolean fallbackMethod(String serialNumber, Throwable throwable){
        log.info("Cannot get availability for machine {}, failure reason: {}", serialNumber, throwable.getMessage());
        return false;
    }
}

//@FeignClient(value = "machineavailability", url = "${machineavailability.service.url}")
//public interface MachineAvailabilityClient {
//
//    @RequestMapping(method = RequestMethod.GET, value = "/api/machine-availability/check")
//    boolean isMachineAvailable(@RequestParam String serialNumber);
//}