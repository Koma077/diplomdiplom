package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserSecurityDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapping.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserDetailsManagerImpl manager;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapper mapper;
    @Spy
    private BCryptPasswordEncoder encoder;
    private final String userName = "aaa@ug.ru";
    private final String password = "123456789";
    private final String renewedPassword = "renewedPassword";
    private final String fName = "Xxx";
    private final String lName = "Xxxxx";
    private final String phone = "+79999999999";
    private final Role role = Role.USER;


    @Test
    void loginTrue() {
        UserSecurityDto userSecurity = getUserSecurity();
        when(userService.userExists(userName)).thenReturn(true);
        when(manager.loadUserByUsername(userName)).thenReturn(userSecurity);
        Assertions.assertTrue(authService.login(userName, password));
    }

    @Test
    void loginIfUserNotExists() {
        when(userService.userExists(userName)).thenReturn(false);
        Assertions.assertFalse(authService.login(userName, password));
    }

    @Test
    void loginIfInvalidPassword() {
        UserSecurityDto userSecurity = getUserSecurity();
        when(userService.userExists(userName)).thenReturn(true);
        when(manager.loadUserByUsername(userName)).thenReturn(userSecurity);
        when(encoder.matches(password, userSecurity.getPassword())).thenReturn(false);
        Assertions.assertFalse(authService.login(userName, password));
    }


    @Test
    void registerIfAlreadyExists() {
        RegisterDto req = getRegisterReq();
        when(userService.userExists(userName)).thenReturn(true);
        Assertions.assertFalse(authService.register(req, role));
    }

    @Test
    void registerSuccessfully() {
        RegisterDto req = getRegisterReq();
        User entity = getEntity();
        when(userService.userExists(userName)).thenReturn(false);
        when(mapper.registerReqDtoToEntity(req)).thenReturn(entity);
        Assertions.assertTrue(authService.register(req, role));
        verify(userService).createUser(entity);
    }


    @Test
    void setPasswordSuccessfully() {
        NewPasswordDto newPassword = new NewPasswordDto();
        String newEncodePass = "123456";
        newPassword.setCurrentPassword(password);
        newPassword.setNewPassword(renewedPassword);
        UserSecurityDto user = getUserSecurity();
        when(manager.loadUserByUsername(userName)).thenReturn(user);
        when(encoder.encode(renewedPassword)).thenReturn(newEncodePass);
        Assertions.assertTrue(authService.setPassword(newPassword, userName));
        verify(userService).changePassword(newEncodePass, userName);
    }

    @Test
    void setPasswordNoMatchingCurrentPassword() {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setCurrentPassword("pass");
        newPassword.setNewPassword(renewedPassword);
        UserSecurityDto user = getUserSecurity();
        when(manager.loadUserByUsername(userName)).thenReturn(user);
        Assertions.assertFalse(authService.setPassword(newPassword, userName));
    }

    private UserSecurityDto getUserSecurity() {
        return new UserSecurityDto(getEntity());
    }

    private RegisterDto getRegisterReq() {
        RegisterDto req = new RegisterDto();
        req.setPhone(phone);
        req.setUsername(userName);
        req.setPassword(password);
        req.setLastName(lName);
        req.setFirstName(fName);
        return req;
    }

    private User getEntity() {
        int id = 1;
        return new User(id, encoder.encode(password), userName, fName, lName, phone, role, null);
    }
}