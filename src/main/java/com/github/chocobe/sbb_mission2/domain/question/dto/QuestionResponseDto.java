package com.github.chocobe.sbb_mission2.domain.question.dto;

import com.github.chocobe.sbb_mission2.domain.question.entity.Question;

import java.time.LocalDateTime;

public record QuestionResponseDto(
        Long id,
        String subject,
        String content,
        LocalDateTime createDate
) {
    public static QuestionResponseDto from(Question question) {
        return new QuestionResponseDto(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                question.getCreateDate()
        );
    }
}