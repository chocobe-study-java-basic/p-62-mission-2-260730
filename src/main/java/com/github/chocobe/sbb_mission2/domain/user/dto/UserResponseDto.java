package com.github.chocobe.sbb_mission2.domain.user.dto;

import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {

    public static UserResponseDto from(SiteUser siteUser) {
        return new UserResponseDto(
                siteUser.getId(),
                siteUser.getUsername(),
                siteUser.getEmail()
        );
    }

}
