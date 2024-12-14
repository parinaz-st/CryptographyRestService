package com.cryptography.service;

import com.cryptography.Utility.JalaliPersianCalender;
import com.cryptography.config.CustomUserDetailManagerImpl;
import com.cryptography.cryptoOperation.CryptographyOperation;
import com.cryptography.dto.UserDto;
import com.cryptography.dto.UserInfoDto;
import com.cryptography.entity.User;
import com.cryptography.entity.UserInfo;
import com.cryptography.mapper.UserMapper;
import com.cryptography.repository.UserInfoRepository;
import com.cryptography.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;


@Service
public class CryptoService {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CryptoService.class);
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
    @Autowired
    UserInfoRepository userInfoRepository;

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
        String persianDate = JalaliPersianCalender.getCurrentPersianDate();
        log.info("User " + userReqDto.getUsername() + "Created on: " + persianDate);
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
    @Transactional(rollbackFor = Exception.class)
    public void addUserInfo(UserInfoDto userInfoDto) throws Exception {
        if (customUserDetailManager.userExists(userInfoDto.getUsername()))
            throw new Exception("User Already Exists");
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        UserInfo userInfo = new UserInfo(userInfoDto);
        userInfoRepository.save(userInfo);
        log.info(userInfo);
    }

}
