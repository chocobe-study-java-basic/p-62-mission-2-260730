package com.github.chocobe.sbb_mission2.domain.answer.repository;

import com.github.chocobe.sbb_mission2.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
