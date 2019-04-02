package com.sc.zhenli.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by songsf on 2019/3/31.
 */
@RestController
@RequestMapping("users")
public class UserController {
    @GetMapping("me")
    public Principal me(Principal principal) {
        return principal;
    }
}
