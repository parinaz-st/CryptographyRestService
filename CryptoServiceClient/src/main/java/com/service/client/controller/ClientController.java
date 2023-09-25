package com.service.client.controller;

import com.service.client.dto.EncryptResDto;
import com.service.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;
    @GetMapping("/call-test")
    public ResponseEntity<String> callTest(){
        return new ResponseEntity<String>(clientService.callTest(), HttpStatus.OK);
    }
    @GetMapping("/call-encrypt")
    public ResponseEntity<EncryptResDto> CallEncrypt(){
        String result = clientService.callEncrypt();
        EncryptResDto encryptResDto = new EncryptResDto();
        encryptResDto.setCipherText(result);
        return new ResponseEntity<EncryptResDto>(encryptResDto, HttpStatus.OK);
    }
}