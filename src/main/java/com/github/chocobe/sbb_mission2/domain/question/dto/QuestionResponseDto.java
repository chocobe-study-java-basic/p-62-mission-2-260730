package com.github.chocobe.sbb_mission2.domain.question.dto;

import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerResponseDto;
import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.user.dto.UserResponseDto;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.global.markdown.MarkdownUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record QuestionResponseDto(
        Long id,
        String subject,
        String content,
        String htmlContent,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        List<AnswerResponseDto> answerList,
        SiteUser author,
        Set<SiteUser> voter
) {
    public static QuestionResponseDto from(
            Question question,
            MarkdownUtil markdownUtil
    ) {
        return new QuestionResponseDto(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                markdownUtil.parseToHtml(question.getContent()),
                question.getCreateDate(),
                question.getModifyDate(),
                question.getAnswerList()
                        .stream()
                        .map(a -> AnswerResponseDto.from(a, markdownUtil))
                        .collect(Collectors.toList()),
                question.getAuthor(),
                question.getVoter()
        );
    }
}