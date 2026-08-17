package com.github.chocobe.sbb_mission2.domain.answer.controller;

import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerFormDto;
import com.github.chocobe.sbb_mission2.domain.answer.service.AnswerService;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{questionId}")
    public String createAnswer(
            Model model,
            @PathVariable Long questionId,
            @ModelAttribute
            @Valid
            AnswerFormDto answerFormDto,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            QuestionResponseDto question = this.questionService.getQuestion(questionId);
            model.addAttribute("question", question);

            return "question_detail";
        }

        this.answerService.create(
                questionId,
                answerFormDto.content(),
                principal.getName()
        );
        return "redirect:/question/detail/%d".formatted(questionId);
    }

}
