package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.Role;


public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterDto registerReq, Role role);

    boolean setPassword(NewPasswordDto newPassword, String name);
}
