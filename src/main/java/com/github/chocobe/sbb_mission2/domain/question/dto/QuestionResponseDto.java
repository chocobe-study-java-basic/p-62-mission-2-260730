package com.github.chocobe.sbb_mission2.domain.question.dto;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionResponseDto(
        Long id,
        String subject,
        String content,
        LocalDateTime createDate,
        List<Answer> answerList
) {
    public static QuestionResponseDto from(Question question) {
        return new QuestionResponseDto(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                question.getCreateDate(),
                question.getAnswerList()
        );
    }
}