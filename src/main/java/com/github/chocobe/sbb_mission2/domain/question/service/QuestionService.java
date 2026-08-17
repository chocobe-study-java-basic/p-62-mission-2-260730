package com.github.chocobe.sbb_mission2.domain.question.service;

import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public Page<QuestionResponseDto> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> questionPage = this.questionRepository.findAll(pageable);
        return questionPage.map(QuestionResponseDto::from);
    }

    @Transactional
    public QuestionResponseDto getQuestion(Long id) {
        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Entity not found"));

        return QuestionResponseDto.from(question);
    }

    public QuestionResponseDto create(
            String subject,
            String content,
            String username
    ) {
        SiteUser author = this.userRepository
                .findByUsername(username)
                .orElseThrow(DataNotFoundException::new);

        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setAuthor(author);

        this.questionRepository.save(question);
        return QuestionResponseDto.from(question);
    }

    public void modify(
            Long id,
            String subject,
            String content
    ) {
        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(DataNotFoundException::new);
        question.setSubject(subject);
        question.setContent(content);

        this.questionRepository.save(question);
    }

    public void delete(Long id, String username) {
        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(DataNotFoundException::new);

        if (!question.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionRepository.delete(question);
    }

}
