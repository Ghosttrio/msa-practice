package com.service1.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service2")
public interface FeignClientCommunicator {

    @RequestMapping(
            method= RequestMethod.GET,
            value="/name/{id}")
    String getName(@PathVariable("id") String id);
}
