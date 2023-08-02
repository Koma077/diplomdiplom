package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.mapping.UserMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService manager;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public boolean login(String userName, String password) {
        if (!userService.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerReq, Role role) {
        if (userService.userExists(registerReq.getUsername())) {
            return false;
        }
        registerReq.setRole(role);
        registerReq.setPassword(encoder.encode(registerReq.getPassword()));
        userService.createUser(mapper.registerReqDtoToEntity(registerReq));
        return true;
    }

    @Override
    public boolean setPassword(NewPasswordDto newPassword, String name) {
        if (encoder.matches(newPassword.getCurrentPassword(), manager.loadUserByUsername(name).getPassword())) {
            userService.changePassword(encoder.encode(newPassword.getNewPassword()), name);
            return true;
        }
        return false;
    }
}
