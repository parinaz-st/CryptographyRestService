package com.cryptography.mapper;

import com.cryptography.dto.UserDto;
import com.cryptography.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
