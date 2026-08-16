package com.github.chocobe.sbb_mission2.domain.answer.controller;

import com.github.chocobe.sbb_mission2.domain.answer.service.AnswerService;
import com.github.chocobe.sbb_mission2.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/{postId}")
    public String createAnswer(
            Model model,
            @PathVariable Long postId,
            @RequestParam String content
    ) {
        this.answerService.create(postId, content);
        return "redirect:/question/detail/%d".formatted(postId);
    }

}
