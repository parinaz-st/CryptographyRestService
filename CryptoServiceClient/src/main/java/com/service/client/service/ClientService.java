package com.service.client.service;

import com.service.client.dto.EncryptReqDto;
import com.service.client.dto.EncryptResDto;
import com.service.client.withoutswagger.resttemplate.CallTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    CallTestService getTestService;
    public String callTest(){
        return getTestService.callGetTest();
    }
    public EncryptResDto callEncrypt(String plainText){
        EncryptReqDto encryptReqDto = new EncryptReqDto();
        encryptReqDto.setPlainText(plainText);
        return getTestService.calPostTest(encryptReqDto);
    }
}
