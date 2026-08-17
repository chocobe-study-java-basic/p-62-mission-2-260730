package com.github.chocobe.sbb_mission2.domain.user.service;

import com.github.chocobe.sbb_mission2.domain.user.dto.UserResponseDto;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(
            String username,
            String email,
            String password
    ) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(this.passwordEncoder.encode(password));

        this.userRepository.save(user);
        return user;
    }

    public UserResponseDto getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository
                .findByUsername(username);

        if (siteUser.isPresent()) {
            return UserResponseDto.from(siteUser.get());
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

}
