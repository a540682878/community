package com.xcy.community.controller;

import com.xcy.community.dto.QuestionDto;
import com.xcy.community.mapper.QuestionMapper;
import com.xcy.community.mapper.UserMapper;
import com.xcy.community.model.Question;
import com.xcy.community.model.User;
import com.xcy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "index";
        } else {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

            List<QuestionDto> questionList = questionService.list();
            model.addAttribute("questions", questionList);
            return "index";
        }
    }
}
