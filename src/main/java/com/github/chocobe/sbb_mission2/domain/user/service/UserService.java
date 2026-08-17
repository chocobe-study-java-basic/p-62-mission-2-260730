package com.github.chocobe.sbb_mission2.domain.user.service;

import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
