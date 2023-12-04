package com.service1.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RibbonClientCommunicator {

    @Autowired
    RestTemplate restTemplate;

    public String getName(String id){

        ResponseEntity<String> restExchange = restTemplate.exchange("http://service2/name/{id}",
                HttpMethod.GET, null, String.class, id);

        return id + " is " + restExchange.getBody();

    }
}
