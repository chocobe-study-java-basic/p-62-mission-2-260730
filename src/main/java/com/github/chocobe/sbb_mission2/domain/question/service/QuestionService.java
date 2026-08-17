package com.github.chocobe.sbb_mission2.domain.question.service;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import com.github.chocobe.sbb_mission2.domain.user.repository.UserRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import com.github.chocobe.sbb_mission2.global.markdown.MarkdownUtil;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final MarkdownUtil markdownUtil;

    public Page<QuestionResponseDto> getList(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = this.search(keyword);

        Page<Question> questionPage = this.questionRepository.findAll(spec, pageable);

        return questionPage.map(question -> QuestionResponseDto.from(
                question,
                this.markdownUtil
        ));
    }

    private Specification<Question> search(String keyword) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(
                    Root<Question> q,
                    CriteriaQuery<?> query,
                    CriteriaBuilder cb
            ) {
                query.distinct(true);

                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);

                return cb.or(cb.like(q.get("subject"), "%" + keyword + "%"), // 제목
                        cb.like(q.get("content"), "%" + keyword + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + keyword + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + keyword + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + keyword + "%"));   // 답변 작성자
            }
        };
    }

    @Transactional
    public QuestionResponseDto getQuestion(Long id) {
        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Entity not found"));

        return QuestionResponseDto.from(
                question,
                this.markdownUtil
        );
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
        return QuestionResponseDto.from(
                question,
                this.markdownUtil
        );
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

    public void vote(Long id, String username) {
        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(DataNotFoundException::new);

        SiteUser user = this.userRepository
                .findByUsername(username)
                .orElseThrow(DataNotFoundException::new);

        question.getVoter().add(user);
        this.questionRepository.save(question);
    }

}
