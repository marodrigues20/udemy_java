package com.in28minutes.microservices.currencyconversionservice.proxy;

import com.in28minutes.microservices.currencyconversionservice.bean.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Because I put spring.application.name=currency-exchange in application.properties
// I use the same name bellow. The line bellow I am not using Eureka Naming Service.
//@FeignClient(name="currency-exchange", url="localhost:8000")

//Line bellow I am using Feign, Eureka (Naming Service) and Eureka give me Spring Load Balance Free.
// I don't need specify url anymore.
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
