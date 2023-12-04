package com.service1.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestTemplateClientCommunicator {

    @Autowired
    private DiscoveryClient discoveryClient;

    public String getName(String id){
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("service2");

        if(instances.size() == 0) return null;

        /** 인스턴스들 중 0번째 클라이언트에 요청 */
        String serviceUri = String.format("%s/name/%s", instances.get(0).getUri().toString(), id);
        ResponseEntity<String> restExchange =
                restTemplate
                        .exchange(serviceUri, HttpMethod.GET, null, String.class, id);

        return id + " is " + restExchange;
    }

    /**
     *  RestTemplate 객체를 생성하여 IP와 PORT로 직접적으로 호출하는 구조입니다.
     *  IP와 PORT를 모른다고 가정하고, 지난장에서 다뤄본 DiscoveryClient를 이용하여,
     *  서비스2의 IP와 Port를 얻어 온 뒤 서비스2의 "/name/name/{id}"API를 호출하고 있습니다.
     *
     *  RestTemplate를 직접 이용할 경우 다음의 문제가 있습니다.
     *
     * 호출하는 서비스에서  IP와 PORT를 관리해야 한다. (아니면 위 예제 처럼 DiscoveryClient를 통해 항상 정보를 가져온 뒤 호출해야 함)
     * 클라이언트 측 부하 분산을 할 수 없다. (Ribbon의 다양한 로드밸런싱 설정 사용 불가)
     */
}
