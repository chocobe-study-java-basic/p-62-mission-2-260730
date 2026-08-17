package com.github.chocobe.sbb_mission2.domain.answer.dto;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.global.markdown.MarkdownUtil;

import java.time.LocalDateTime;
import java.util.Set;

public record AnswerResponseDto(
        Long id,
        String content,
        String htmlContent,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        Question question,
        SiteUser author,
        Set<SiteUser> voter
) {

    public static AnswerResponseDto from(
            Answer answer,
            MarkdownUtil markdownUtil
    ) {
        return new AnswerResponseDto(
                answer.getId(),
                answer.getContent(),
                markdownUtil.parseToHtml(answer.getContent()),
                answer.getCreateDate(),
                answer.getModifyDate(),
                answer.getQuestion(),
                answer.getAuthor(),
                answer.getVoter()
        );
    }
}
