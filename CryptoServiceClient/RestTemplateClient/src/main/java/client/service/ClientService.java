package client.service;

import client.dto.EncryptReqDto;
import client.dto.EncryptResDto;
import client.withoutswagger.resttemplate.CallTestService;
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
