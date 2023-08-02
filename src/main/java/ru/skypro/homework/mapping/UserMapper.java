package ru.skypro.homework.mapping;


import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public UserDto entityToUserDto(User entity) {
        return new UserDto(entity.getId(), entity.getEmail(), entity.getFirstName(),
                entity.getLastName(), entity.getPhone(), entity.getImagePath());
    }

    public User userDtoToEntity(UserDto user, User entity) {
        entity.setPhone(user.getPhone());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        return entity;
    }

    public User registerReqDtoToEntity(RegisterDto req) {
        return new User(req.getPassword(), req.getUsername(), req.getFirstName(),
                req.getLastName(), req.getPhone(), req.getRole());
    }
}
