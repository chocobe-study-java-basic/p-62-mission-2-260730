package com.github.chocobe.sbb_mission2.domain.question.controller;

import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<QuestionResponseDto> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(
            Model model,
            @PathVariable Long id
    ) {
        QuestionResponseDto question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

}
