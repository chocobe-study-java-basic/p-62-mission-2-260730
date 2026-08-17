package com.github.chocobe.sbb_mission2.domain.answer.service;

import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerResponseDto;
import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.answer.repository.AnswerRepository;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerResponseDto create(Long questionId, String content, String username) {
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

        this.answerRepository.save(answer);
        return AnswerResponseDto.from(answer);
    }

    public AnswerResponseDto getAnswer(Long id) {
        Answer answer = this.answerRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Answer not found"));

        return AnswerResponseDto.from(answer);
    }

    public AnswerResponseDto modify(long id, String content, String username) {
        Answer answer = this.answerRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("answer not found"));

        if (!answer.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answer.setContent(content);
        this.answerRepository.save(answer);

        return AnswerResponseDto.from(answer);
    }

    public AnswerResponseDto delete(Long id, String username) {
        Answer answer = this.answerRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Answer not found"));

        if (!answer.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.answerRepository.delete(answer);
        return AnswerResponseDto.from(answer);
    }

    public AnswerResponseDto vote(Long id, String username) {
        Answer answer = this.answerRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Answer not found"));

        SiteUser user =  this.userRepository
                .findByUsername(username)
                .orElseThrow(DataNotFoundException::new);

        answer.getVoter().add(user);
        this.answerRepository.save(answer);

        return AnswerResponseDto.from(answer);
    }

}
