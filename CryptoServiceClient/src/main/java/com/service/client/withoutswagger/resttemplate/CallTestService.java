package com.service.client.withoutswagger.resttemplate;

import com.service.client.dto.EncryptResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CallTestService {
    @Autowired
    RestTemplate restTemplate;

    @Value("crypto.server.encrypt.url")
    private String encryptUrl;
    public String callGetTest(){
        String url = "http://localhost:9097/test";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    public EncryptResDto calPostTest(String plainText){
        return restTemplate.postForObject(encryptUrl, plainText, EncryptResDto.class);
    }

}
