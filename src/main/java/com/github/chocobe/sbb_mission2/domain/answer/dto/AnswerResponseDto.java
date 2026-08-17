package com.github.chocobe.sbb_mission2.domain.answer.dto;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;

public record AnswerResponseDto(
        Long id,
        String content,
        Question question,
        SiteUser author
) {

    public static AnswerResponseDto from(Answer answer) {
        return new AnswerResponseDto(
                answer.getId(),
                answer.getContent(),
                answer.getQuestion(),
                answer.getAuthor()
        );
    }
}
