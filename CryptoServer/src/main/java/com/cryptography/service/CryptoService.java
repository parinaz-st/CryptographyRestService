package com.cryptography.service;

import com.cryptography.config.CustomUserDetailManagerImpl;
import com.cryptography.cryptoOperation.CryptographyOperation;
import com.cryptography.dto.UserDto;
import com.cryptography.entity.User;
import com.cryptography.mapper.UserMapper;
import com.cryptography.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CryptoService {
    @Autowired
    CryptographyOperation cryptographyOperation;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomUserDetailManagerImpl customUserDetailManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String encryptString(String requestBody) {
        return cryptographyOperation.getEncryptWithJavaGeneratedIV(requestBody);
    }

    public String decryptString(String cipherText) {
        return cryptographyOperation.getDecrypt(cipherText);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto ceateUser(UserDto userReqDto) {

        if (customUserDetailManager.userExists(userReqDto.getUsername()))
            return new UserDto("User Already Exists", "", "");
        userReqDto.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        User user = userMapper.toEntity(userReqDto);
        return userMapper.toDto(userRepository.save(user));
    }
    public UserDto createUserWithSpringSecurity(UserDto userReqDto)
    {
        if (customUserDetailManager.userExists(userReqDto.getUsername()))
            return new UserDto("User Already Exists", "", "");

        User user = userMapper.toEntity(userReqDto);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        customUserDetailManager.createUser(userDetails);
        return new UserDto("User created", "", "");
    }

    public String signText(String text) {
        return cryptographyOperation.signText(text);
    }
}
