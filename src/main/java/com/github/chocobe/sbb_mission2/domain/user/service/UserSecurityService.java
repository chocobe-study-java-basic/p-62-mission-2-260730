package com.github.chocobe.sbb_mission2.domain.user.service;

import com.github.chocobe.sbb_mission2.domain.user.dto.UserRole;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository
                .findByUsername(username);

        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(siteUser.getUsername())) {
            authorities.add(
                    new SimpleGrantedAuthority(UserRole.ADMIN.getValue())
            );
        } else {
            authorities.add(
                    new SimpleGrantedAuthority(UserRole.USER.getValue())
            );
        }

        return new User(
                siteUser.getUsername(),
                siteUser.getPassword(),
                authorities
        );
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
