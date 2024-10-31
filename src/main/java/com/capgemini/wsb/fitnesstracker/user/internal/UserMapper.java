package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    BasicUserDto toBasicDto(User user) {
        return new BasicUserDto(user.getId(),
                        user.getFirstName(),
                        user.getLastName());
    }

    UserIdDto IdsDto(User user) {
        return new UserIdDto(
                user.getId(),
                user.getEmail()
        );
    }

    User toUpdateEntity(UserDto userDto, User user) {
        if(userDto.firstName() != null) {
            user.setFirstName(userDto.firstName());
        }
        if(userDto.lastName() != null) {
            user.setLastName(userDto.lastName());
        }
        if(userDto.birthdate() != null) {
            user.setBirthdate(userDto.birthdate());
        }
        if(userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        return user;
    }
}
