package com.github.chocobe.sbb_mission2.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserCreateForm(
        @NotEmpty(message = "사용자ID는 필수항목입니다.")
        @Size(min = 3, max = 25)
        String username,

        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        String password1,

        @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
        String password2,

        @NotEmpty(message = "이메일은 필수항목입니다.")
        @Email
        String email
) {
}
