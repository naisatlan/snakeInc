package com.snakeinc.api.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/hello")
public class HelloController {

    @GetMapping
    public String HelloController(@RequestParam String name) {
        return "Hello " + name + "!";
    }

    @PostMapping
    public String postHelloController(@RequestBody BodyParam name) {
        return "post " + name.name();
    }

    public record BodyParam(String name) {
    }

}