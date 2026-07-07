package com.jxl.studentmanger.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TsetController {

    @RequestMapping("/sayhello")
    public String sayhello(@RequestParam("username") String name){
        return "hasda" + name;
    }
}
