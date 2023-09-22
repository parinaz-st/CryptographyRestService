package com.service.client.withoutswagger.resttemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class GetTestService {

    public String callTest(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9097/test";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

}
