package com.service1.service;

import com.service1.rest.FeignClientCommunicator;
import com.service1.rest.RestTemplateClientCommunicator;
import com.service1.rest.RibbonClientCommunicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    RestTemplateClientCommunicator restTemplateClientCommunicator;
    @Autowired
    RibbonClientCommunicator ribbonClientCommunicator;

    @Autowired
    FeignClientCommunicator feignClientCommunicator;

    public List getServices(){
        ArrayList<String> services = new ArrayList<>();
        discoveryClient.getServices().forEach(serviceName -> {
            discoveryClient.getInstances(serviceName).forEach(instance -> {
                services.add(String.format("%s:%s", serviceName, instance.getUri()));
            });
        });
        return services;
    }

//    DiscoveryClient는 유레카 클라이언트에서 제공하는 객체로서 서비스들의 정보를 찾아서 제공해주는 객체 입니다.

    public String resttemplate(String id) {
        return restTemplateClientCommunicator.getName(id);
    }

    public String ribbon(String id) {
        return ribbonClientCommunicator.getName(id);
    }

    public String feign(String id) {
        return id + " is " + feignClientCommunicator.getName(id);
    }

}
