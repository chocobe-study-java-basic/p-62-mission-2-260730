package com.github.chocobe.sbb_mission2.domain.question.service;

import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
