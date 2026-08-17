package com.github.chocobe.sbb_mission2.domain.question.controller;

import com.github.chocobe.sbb_mission2.domain.answer.dto.AnswerFormDto;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionFormDto;
import com.github.chocobe.sbb_mission2.domain.question.dto.QuestionResponseDto;
import com.github.chocobe.sbb_mission2.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

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
            int page,
            @RequestParam(
                    value = "keyword",
                    defaultValue = ""
            )
            String keyword
    ) {
        Page<QuestionResponseDto> paging = this.questionService.getList(page, keyword);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(
            @ModelAttribute
            QuestionFormDto questionFormDto
    ) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(
            @ModelAttribute
            @Valid
            QuestionFormDto questionFormDto,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        this.questionService.create(
                questionFormDto.subject(),
                questionFormDto.content(),
                principal.getName()
        );

        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(
            Model model,
            @PathVariable Long id,
            Principal principal
    ) {
        QuestionResponseDto question = this.questionService.getQuestion(id);

        if (question == null
                || !question.author().getUsername().equals(principal.getName())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        model.addAttribute("questionFormDto", question);
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(
            @PathVariable Long id,
            @ModelAttribute
            @Valid
            QuestionFormDto questionFormDto,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        this.questionService.modify(
                id,
                questionFormDto.subject(),
                questionFormDto.content()
        );

        return "redirect:/question/detail/%d".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(
            @PathVariable Long id,
            Principal principal
    ) {
        this.questionService.delete(id, principal.getName());
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(
            @PathVariable Long id,
            Principal principal
    ) {
        this.questionService.vote(
                id,
                principal.getName()
        );
        return "redirect:/question/detail/%d".formatted(id);
    }

}
