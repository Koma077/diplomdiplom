package ru.skypro.homework.mapping;

import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class UserMapperTest {

    private final UserMapper mapper = new UserMapper();
    private final int id = 1;
    private final String password = "123456";
    private final String email = "xxx@mail.ru";
    private final String fName = "Xxx";
    private final String lName = "Xxxxx";
    private final String phone = "++79898989898";
    private final Role role = Role.USER;

    @Test
    void entityToUserDtoTestNoImage() {
        User entity = new User(id, password, email, fName, lName, phone, role, null);
        UserDto user = mapper.entityToUserDto(entity);
        assert user.getId() == id;
        assert user.getEmail().equals(email);
        assert user.getFirstName().equals(fName);
        assert user.getLastName().equals(lName);
        assert user.getPhone().equals(phone);
        Assertions.assertNull(user.getImage());
    }

    @Test
    void entityToUserDtoTestWithImage() {
        User entity = new User(id, password, email, fName, lName, phone, role,
                new Image(1));
        UserDto user = mapper.entityToUserDto(entity);
        assert user.getId() == id;
        assert user.getEmail().equals(email);
        assert user.getFirstName().equals(fName);
        assert user.getLastName().equals(lName);
        assert user.getPhone().equals(phone);
        assert user.getImage().equals(entity.getImagePath());
    }

    @Test
    void userDtoToEntityTest() {
        String newFName = "Zzz";
        String newLName = "Zzzzz";
        String newPhone = "+79898989898";
        UserDto user = new UserDto(id, email, fName, lName, phone, null);
        User entity = new User(id, password, email, newFName, newLName, newPhone, role, null);
        User newEntity = mapper.userDtoToEntity(user, entity);
        assert newEntity.getFirstName().equals(fName);
        assert newEntity.getLastName().equals(lName);
        assert newEntity.getPhone().equals(phone);
    }

    @Test
    void registerReqDtoToEntityTest() {
        RegisterDto req = new RegisterDto();
        req.setPassword(password);
        req.setRole(role);
        req.setUsername(email);
        req.setLastName(lName);
        req.setFirstName(fName);
        req.setPhone(phone);
        User entity = mapper.registerReqDtoToEntity(req);
        assert entity.getPhone().equals(phone);
        assert entity.getLastName().equals(lName);
        assert entity.getEmail().equals(email);
        assert entity.getFirstName().equals(fName);
        assert entity.getRole().equals(role);
        assert entity.getPassword().equals(password);
    }
}