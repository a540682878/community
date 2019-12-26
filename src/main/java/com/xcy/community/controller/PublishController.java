package com.xcy.community.controller;

import com.xcy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @GetMapping("/publish")
    public String topublish(){
        return "publish";
    }

    @Autowired
    QuestionService questionService;

    @PostMapping("/publish")
    public String dopublish(@RequestParam(name = "title")String title,
                            @RequestParam(name = "description")String description,
                            @RequestParam(name = "tag")String tag,
                            HttpServletRequest request){
        questionService.getQuestion(title,description,tag,request);
        return "publish";
    }
}
