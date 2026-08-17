package com.github.chocobe.sbb_mission2.domain.question.dto;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.user.dto.UserResponseDto;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record QuestionResponseDto(
        Long id,
        String subject,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        List<Answer> answerList,
        SiteUser author,
        Set<SiteUser> voter
) {
    public static QuestionResponseDto from(Question question) {
        return new QuestionResponseDto(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                question.getCreateDate(),
                question.getModifyDate(),
                question.getAnswerList(),
                question.getAuthor(),
                question.getVoter()
        );
    }
}