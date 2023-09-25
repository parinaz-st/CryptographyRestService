package com.service.client.withoutswagger.resttemplate;

import com.service.client.dto.EncryptReqDto;
import com.service.client.dto.EncryptResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CallTestService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${crypto.server.encrypt.url}")
    private String encryptUrl;
    public String callGetTest(){
        String url = "http://localhost:9097/test";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    public String calPostTest(){
        EncryptReqDto encryptReqDto = new EncryptReqDto();
        encryptReqDto.setPlainText("ParinazZ");
        HttpEntity<EncryptReqDto> httpEntity = new HttpEntity<>(encryptReqDto);
       return restTemplate.postForObject(encryptUrl, httpEntity, String.class);
//        return restTemplate.exchange(encryptUrl, HttpMethod.POST, httpEntity, Object.class);
    }

}
