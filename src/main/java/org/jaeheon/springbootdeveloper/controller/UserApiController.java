package org.jaeheon.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.dto.AddUserRequest;
import org.jaeheon.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        // After signing up by adding a prefix, go to the /login URL.
        return "redirect:/login";
    }
}
