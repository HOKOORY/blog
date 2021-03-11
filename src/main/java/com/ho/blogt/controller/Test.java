package com.ho.blogt.controller;

import com.ho.blogt.entity.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @RequestMapping("/test")
    public Object test(@RequestParam(name = "id") String id) {
        return new Response<>(id);
    }
}
