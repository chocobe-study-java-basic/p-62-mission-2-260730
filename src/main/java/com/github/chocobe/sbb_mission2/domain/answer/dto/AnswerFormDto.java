package com.github.chocobe.sbb_mission2.domain.answer.dto;

import jakarta.validation.constraints.NotEmpty;

public record AnswerFormDto(
        @NotEmpty(message = "내용은 필수항목입니다.")
        String content
) {
}
