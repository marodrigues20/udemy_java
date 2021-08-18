package com.minutes.microservices.limitsservice.controller;


import com.minutes.microservices.limitsservice.bean.Limits;
import com.minutes.microservices.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    Configuration configuraiton;

    @GetMapping("/limits")
    public Limits retrieveLimits(){
        return new Limits(configuraiton.getMinimum(),configuraiton.getMaximum());
    }
}
