package com.cryptography.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {
    @GetMapping("/test")
    public ResponseEntity printHelloWorld()
    {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }
}
