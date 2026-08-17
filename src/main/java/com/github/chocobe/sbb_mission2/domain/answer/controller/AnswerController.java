package com.github.chocobe.sbb_mission2.domain.answer.controller;

import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerFormDto;
import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerResponseDto;
import com.github.chocobe.sbb_mission2.domain.answer.service.AnswerService;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(
            Model model,
            @PathVariable
            Long id,
            @Valid
            Principal principal
    ) {
        AnswerResponseDto answer = this.answerService.getAnswer(id);

        if (answer == null
                || !answer.author().getUsername().equals(principal.getName())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        model.addAttribute("answerFormDto", answer);
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(
            @PathVariable Long id,
            @ModelAttribute
            @Valid
            AnswerFormDto answerFormDto,
            BindingResult bindingResult,
            Principal principal
    ) {
        if(bindingResult.hasErrors()) {
            return "answer_form";
        }

        AnswerResponseDto answer = this.answerService.modify(
                id,
                answerFormDto.content(),
                principal.getName()
        );

        return "redirect:/question/detail/%s"
                .formatted(answer.question().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(
            @PathVariable
            Long id,
            Principal principal
    ) {
        AnswerResponseDto answer = this.answerService.delete(id, principal.getName());
        return "redirect:/question/detail/%d".formatted(answer.author().getId());
    }

}
