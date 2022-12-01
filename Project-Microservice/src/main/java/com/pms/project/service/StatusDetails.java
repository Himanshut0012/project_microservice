package com.pms.project.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StatusDetails {

    private static final String STATUS_DETAILS="statusDetails";
    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "STATUS_DETAILS", fallbackMethod = "fallBackKnowProjectStatus")
    public String knowProjectStatus(int projectStatusId){
        return  this.restTemplate.getForObject("http://project-status/api/v1/status/" + projectStatusId, String.class);
    }
    public String fallBackKnowProjectStatus(int projectStatusId, Exception e){
        return "Status service down";
    }
}
