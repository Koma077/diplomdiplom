package ru.skypro.homework.service;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {
    UserDto update(String name, UserDto user);

    void delete(String name);

    UserDto get(String name);

    User getEntity(String name);

    void uploadImage(MultipartFile image, String name) throws IOException;

    User getEntityById(int id);
    void changePassword(String newPassword, String name);
    boolean userExists(String username);
    void createUser(User user);
}

