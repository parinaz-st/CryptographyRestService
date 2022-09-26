package com.cryptography;

import com.cryptography.entity.User;
import com.cryptography.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CryptographyApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args){
        SpringApplication.run(CryptographyApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("admin",passwordEncoder.encode("Asdfgh123456") , "ADMIN");
        userRepository.save(user);
    }
}
