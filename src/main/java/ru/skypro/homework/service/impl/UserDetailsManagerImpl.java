package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.UserSecurityDto;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserDetailsManagerImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserSecurityDto(repository.findByEmail(username)
                .orElseThrow(() -> new FindNoEntityException("пользователь")));
    }
}
