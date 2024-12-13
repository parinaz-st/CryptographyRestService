package com.cryptography.controller;

import com.cryptography.dto.*;
import com.cryptography.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CryptoController {

    @Autowired
    CryptoService cryptoService;
    @GetMapping("/test")
    public ResponseEntity printHelloWorld()
    {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }

    @PostMapping("/admin/createUser")
    public ResponseEntity<UserDto> ceateUser(@RequestBody UserDto userReqDto)
    {
        UserDto userDto  = cryptoService.ceateUser(userReqDto);
        userDto.setPassword(null);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/admin/createUserWithSpringSecurity")
    public ResponseEntity<UserDto> createUserWithSpringSecurity(@RequestBody UserDto userReqDto)
    {
        UserDto userDto  = cryptoService.createUserWithSpringSecurity(userReqDto);
        userDto.setPassword(null);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/encrypt")
    public ResponseEntity<EncryptResDto> encryptString(@RequestBody EncryptReqDto encryptReqDto)
    {
        String result = cryptoService.encryptString(encryptReqDto.getPlainText());
        EncryptResDto encryptResDto = new EncryptResDto();

        if (result != null)
        {
            encryptResDto.setCipherText(result);
        }
        else{
            encryptResDto.setCipherText("Something wicked this way comes");
        }
        return new ResponseEntity<>(encryptResDto, HttpStatus.OK);
    }
    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptString(@RequestBody DecryptReqDto decryptReqDto)
    {
        String result = cryptoService.decryptString(decryptReqDto.getCipherText());
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something wicked this way comes", HttpStatus.OK);
    }
    @PostMapping("/sign")
    public ResponseEntity<String> Sign(@RequestBody ToBeSignedDto toBeSignedDto)
    {
        String signedTest = cryptoService.signText(toBeSignedDto.getText());

        return new ResponseEntity<>(signedTest, HttpStatus.OK);
    }

}
