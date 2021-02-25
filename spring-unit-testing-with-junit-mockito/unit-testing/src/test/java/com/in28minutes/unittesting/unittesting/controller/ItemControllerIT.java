package com.in28minutes.unittesting.unittesting.controller;


import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    //@MockBean Can be used to isolate a layer that can broke your integration test.
    //For example one layer that consume data from one external service.

    @Test
    public void contextLoads() throws JSONException {

        String response = this.restTemplate.getForObject("/all-items-from-database", String.class);

        JSONAssert.assertEquals("[{id:1001},{id:1002},{id:1003}]",response, false);

    }
}
