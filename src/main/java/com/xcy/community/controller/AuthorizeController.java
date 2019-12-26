package com.xcy.community.controller;

import com.xcy.community.dto.AccessTokenDto;
import com.xcy.community.dto.GithubUser;
import com.xcy.community.mapper.UserMapper;
import com.xcy.community.model.User;
import com.xcy.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
//@Component
//@ConfigurationProperties(prefix = "github")
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserMapper userMapper;

    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    //code和state是从<a href>中获取
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response, HttpServletRequest request) throws IOException {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        //setClient_id为GitHub账户下OAuth Apps内Client ID
        accessTokenDto.setClient_id(client_id);
        //setClient_secret为GitHub账户下OAuth Apps内Client Secret
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        //setRedirect_uri为GitHub账户下OAuth Apps内Authorization callback URL
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);

        //通过POST请求方式将设置好的accessTokenDto信息发送给https://github.com/login/oauth/access_token
        //并返回access token
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //通过GET请求方式，在access token中获取user的信息
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser!=null){
            User user = new User();

            String token = UUID.randomUUID().toString();
            user.setToken(token);

            user.setAccoundId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userMapper.insertUser(user);

            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{

            return "redirect:/";
        }
    }
}
