package com.pms.project.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BillingDetails {

    private static final String BILLING_DETAILS="billingDetails";
    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "BILLING_DETAILS",fallbackMethod = "fallBackKnowBillingType")
    public String knowBillingType(int billingId) {
        return this.restTemplate.getForObject("http://project-billing/api/v1/billing/" + billingId, String.class);
    }

    public String fallBackKnowBillingType(int billingId, Exception e){
        return "Billing service down";
    }
}
