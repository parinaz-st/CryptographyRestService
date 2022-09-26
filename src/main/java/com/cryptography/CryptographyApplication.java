package com.cryptography;

import com.cryptography.config.CustomUserDetailManagerImpl;
import com.cryptography.entity.User;
import com.cryptography.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CryptographyApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailManagerImpl userDetailManager;

    public static void main(String[] args){
        SpringApplication.run(CryptographyApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("Asdfgh123456")
                .roles("َADMIN")
                .build();
        userDetailManager.createUser(userDetails);
//        User user = new User("admin",passwordEncoder.encode("Asdfgh123456") , "[ROLE_ADMIN]");
//        User user = new User("admin","123456" , "ADMIN");
    }
}
