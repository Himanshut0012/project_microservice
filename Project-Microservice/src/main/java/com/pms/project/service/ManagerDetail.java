package com.pms.project.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManagerDetail {

    private static final String MANAGER_NAME="managerName";
    @Autowired
    private RestTemplate restTemplate;


    @CircuitBreaker(name = "MANAGER_NAME",fallbackMethod = "KnowManagerNameFallBack")
    public String KnowManagerName(int managerId) {
        return this.restTemplate.getForObject("http://employee-microservice/api/v1/employee/find/" + managerId, String.class);
    }

    public String KnowManagerNameFallBack(int managerId, Exception e){
        return "Employee service down";
    }
}
