package com.xcy.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcy.community.dto.QuestionDto;
import com.xcy.community.mapper.UserMapper;
import com.xcy.community.model.Question;
import com.xcy.community.model.User;
import com.xcy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(defaultValue = "1") Integer pn,
                        @RequestParam(defaultValue = "5") Integer pageSize,
                        Model model) {

        Page p = PageHelper.startPage(pn, pageSize);
        //调用questionServices.questionPage()方法,得到List<QuestionDTO>
        //PageInfo<Question>内集合了所有的question信息即分页功能
        PageInfo info = new PageInfo<>(questionService.list(), 5);
        model.addAttribute("pageInfo", info);
        return "index";
    }

    //退出登录
    @GetMapping("/outlogin")
    public String oulLogin(HttpSession session,
                            HttpServletRequest request,
                           HttpServletResponse response){

        session.removeAttribute("user");
        Cookie cookies[]=request.getCookies();
        for(Cookie cookie: cookies){
            if("token".equals(cookie.getName())){
                //删除
                cookie.setMaxAge(0);
                response.addCookie(cookie);//一定带上这句话，否则不能删除
            }
        }

        return "redirect:/";
    }

}
