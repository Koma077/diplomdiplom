package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapping.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDto update(String name, UserDto user) {
        return mapper.entityToUserDto(userRepository.save(mapper.userDtoToEntity(user, getEntity(name))));
    }

    @Override
    public void delete(String name) {
        userRepository.deleteByEmail(name);
    }

    @Override
    public UserDto get(String name) {
        return mapper.entityToUserDto(getEntity(name));
    }

    @Override
    public User getEntity(String name) {
        return userRepository.findByEmail(name).orElseThrow(() -> new FindNoEntityException("пользователь"));
    }

    @Override
    public void uploadImage(MultipartFile image, String name) throws IOException {
        User userEntity = getEntity(name);
        Image imageEntity = userEntity.getImage();
        userEntity.setImage(imageService.saveImage(image));
        userRepository.save(userEntity);
        if (imageEntity != null) {
            imageService.deleteImage(imageEntity);
        }
    }

    @Override
    public User getEntityById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new FindNoEntityException("пользователь"));
    }

    @Override
    public void changePassword(String newPassword, String name) {
        User entity = getEntity(name);
        entity.setPassword(newPassword);
        userRepository.save(entity);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
}
