package com.github.chocobe.sbb_mission2.domain.question.service;

import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import com.github.chocobe.sbb_mission2.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> getList() {
        List<Question> questionList = this.questionRepository.findAll();
        return questionList.stream()
                .map(QuestionResponseDto::from)
                .toList();
    }

    public QuestionResponseDto getQuestion(Long id) {
        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Entity not found"));

        return QuestionResponseDto.from(question);
    }

}
