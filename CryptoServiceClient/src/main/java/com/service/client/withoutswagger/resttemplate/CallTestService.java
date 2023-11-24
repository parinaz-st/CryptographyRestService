package com.service.client.withoutswagger.resttemplate;

import com.service.client.dto.EncryptReqDto;
import com.service.client.dto.EncryptResDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CallTestService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${crypto.server.encrypt.url}")
    private String encryptUrl;
    @Value("${crypto.server.user}")
    private String username;
    @Value("${crypto.server.pass}")
    private String password;
    public String callGetTest(){
        String url = "http://localhost:9097/test";
        // TO-DO bug FIX
//        HttpHeaders headers = initHeaders();
//        HttpEntity<EncryptReqDto> httpEntity = new HttpEntity<EncryptReqDto>(reqDto, headers);
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    public EncryptResDto calPostTest(EncryptReqDto reqDto){
        HttpHeaders headers = initHeaders();
        HttpEntity<EncryptReqDto> httpEntity = new HttpEntity<EncryptReqDto>(reqDto, headers);
        return restTemplate.postForObject(encryptUrl, httpEntity, EncryptResDto.class);

    }

    private HttpHeaders initHeaders() {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            Base64 base64 = new Base64();
            String authHeader = "Basic " + new String(base64.encode(auth.getBytes()));
            set("Authorization", authHeader);
        }};
    }


}
