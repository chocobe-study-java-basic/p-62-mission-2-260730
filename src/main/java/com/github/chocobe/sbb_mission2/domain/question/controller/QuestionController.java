package com.github.chocobe.sbb_mission2.domain.question.controller;

import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerFormDto;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionFormDto;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(
            Model model,
            @RequestParam(
                    value = "page",
                    defaultValue = "0"
            )
            int page
    ) {
        Page<QuestionResponseDto> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);

        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(
            Model model,
            @PathVariable Long id,
            @ModelAttribute
            AnswerFormDto answerFormDto
    ) {
        QuestionResponseDto question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(
            @ModelAttribute
            QuestionFormDto questionFormDto
    ) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(
            @ModelAttribute
            @Valid
            QuestionFormDto questionFormDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        this.questionService.create(
                questionFormDto.subject(),
                questionFormDto.content()
        );

        return "redirect:/question/list";
    }

}
