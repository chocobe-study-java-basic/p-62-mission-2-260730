package com.github.chocobe.sbb_mission2.domain.answer.service;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.answer.repository.AnswerRepository;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public Answer create(Long questionId, String content) {
        Question question = this.questionRepository
                .findById(questionId)
                .orElseThrow(DataNotFoundException::new);

        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());

        this.answerRepository.save(answer);
        return answer;
    }

}
