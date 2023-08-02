package ru.skypro.homework.controller;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto newPassword,
                                         Authentication authentication) {
        if (authService.setPassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok( userService.get(authentication.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto newUser, Authentication authentication) {
        return ResponseEntity.ok(userService.update(authentication.getName(), newUser));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestPart MultipartFile image, Authentication auth) throws IOException {
        userService.uploadImage(image, auth.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) throws IOException {
        long imageId = userService.getEntityById(id).getImage().getId();
        return ResponseEntity.ok(imageService.getImage(imageId));
    }
}
