package com.github.chocobe.sbb_mission2.domain.answer.service;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.answer.repository.AnswerRepository;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public Answer create(Long questionId, String content, String username) {
        Question question = this.questionRepository
                .findById(questionId)
                .orElseThrow(DataNotFoundException::new);

        SiteUser author = this.userRepository
                .findByUsername(username)
                .orElseThrow(DataNotFoundException::new);

        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setAuthor(author);
        answer.setCreateDate(LocalDateTime.now());

        this.answerRepository.save(answer);
        return answer;
    }

}
