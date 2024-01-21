package com.cryptography.controller;

import com.cryptography.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/readfile")
    public ResponseEntity<String> readFile() throws IOException {
        fileService.readFile();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @GetMapping("/encryptfile")
    public ResponseEntity<String> encryptFile(){

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
