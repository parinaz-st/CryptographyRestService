package com.cryptography.service;

import com.cryptography.cryptography.CryptographyOperation;
import com.cryptography.dto.UserDto;
import com.cryptography.entity.User;
import com.cryptography.mapper.UserMapper;
import com.cryptography.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String encryptString(String requestBody) {
        return cryptographyOperation.getEncryptWithJavaGeneratedIV(requestBody);
    }

    public String decryptString(String cipherText) {
        return cryptographyOperation.getDecrypt(cipherText);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto ceateUser(UserDto userReqDto) {
        User user = userMapper.toEntity(userReqDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
