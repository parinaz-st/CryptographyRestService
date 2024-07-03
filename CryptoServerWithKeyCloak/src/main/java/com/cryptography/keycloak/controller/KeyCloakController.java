package com.cryptography.keycloak.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ForKeyCloak")
public class KeyCloakController {
//    @PreAuthorize
    @GetMapping("/greetingsauth")
    public ResponseEntity<String> greetingsWithAuth(){
        return new ResponseEntity<>( "Hello world", HttpStatus.OK);
    }
    @GetMapping("/greetingsnoauth")
    public ResponseEntity<String> greetingsnoAuth(){
        return new ResponseEntity<>( "Hello world", HttpStatus.OK);
    }
}
