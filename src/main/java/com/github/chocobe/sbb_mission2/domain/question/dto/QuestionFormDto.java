package com.github.chocobe.sbb_mission2.domain.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record QuestionFormDto(
        @NotEmpty(message = "제목은 필수항목입니다.")
        @Size(max = 200)
        String subject,

        @NotEmpty(message = "내용은 필수항목입니다.")
        String content
) {
}
