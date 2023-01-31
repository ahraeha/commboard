package com.jjez.commboard.user.controller;

import com.jjez.commboard.user.model.UserInput;
import com.jjez.commboard.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;

  @GetMapping("/user/register")
  public String register() {
    return "user/register";
  }

  @PostMapping("/user/register")
  public String registerSubmit(Model model, HttpServletRequest request
  , HttpServletResponse response, UserInput parameter) {

    boolean result = userService.register(parameter);

    model.addAttribute("result", result);

    return "user/register_success";
  }

}
