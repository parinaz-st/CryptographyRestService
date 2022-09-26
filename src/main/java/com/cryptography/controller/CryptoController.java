package com.cryptography.controller;

import com.cryptography.dto.DecryptReqDto;
import com.cryptography.dto.EncryptReqDto;
import com.cryptography.dto.UserDto;
import com.cryptography.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CryptoController {

    @Autowired
    CryptoService cryptoService;
    @GetMapping("/test")
    public ResponseEntity printHelloWorld()
    {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userReqDto)
    {
        UserDto userDto  = cryptoService.ceateUser(userReqDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptString(@RequestBody EncryptReqDto encryptReqDto)
    {
        String result = cryptoService.encryptString(encryptReqDto.getPlainText());

        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something wicked this way comes", HttpStatus.OK);
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
}
