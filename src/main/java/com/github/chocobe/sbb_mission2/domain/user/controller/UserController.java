package com.github.chocobe.sbb_mission2.domain.user.controller;

import com.github.chocobe.sbb_mission2.domain.user.dto.UserCreateForm;
import com.github.chocobe.sbb_mission2.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String signup(
            @ModelAttribute
            UserCreateForm userCreateForm
    ) {
        return "signup_form";
    }

    @PostMapping
    public String signup(
            @ModelAttribute
            @Valid
            UserCreateForm userCreateForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.password1().equals(userCreateForm.password2())) {
            bindingResult.rejectValue(
                    "password2",
                    "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다."
            );
            return "signup_form";
        }

        try {
            this.userService.create(
                    userCreateForm.username(),
                    userCreateForm.email(),
                    userCreateForm.password1()
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace(System.err);
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

}
