package com.github.chocobe.sbb_mission2.domain.question.repository;

import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findBySubject(String subject);

    Optional<Question> findBySubjectAndContent(String subject, String content);

    List<Question> findAllBySubjectLike(String subject);

}
