package com.xcy.community.controller;

import com.xcy.community.dto.AccessTokenDto;
import com.xcy.community.dto.GithubUser;
import com.xcy.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
//@Component
//@ConfigurationProperties(prefix = "github")
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    //code和state是从<a href>中获取
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) throws IOException {
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
        System.out.println(githubUser.getName());
        return "index";
    }
}
