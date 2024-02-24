package com.nlthanhcse.facebook.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/")
    public String hallo() {
        return "Hallo from " + applicationName;
    }
}
