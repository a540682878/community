package com.xcy.community.service;

import com.xcy.community.dto.QuestionDto;
import com.xcy.community.mapper.QuestionMapper;
import com.xcy.community.mapper.UserMapper;
import com.xcy.community.model.Question;
import com.xcy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    public Boolean getQuestion(@RequestParam(name = "title")String title,
                                 @RequestParam(name = "description")String description,
                                 @RequestParam(name = "tag")String tag,
                                 HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Question question = new Question();

        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreat(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreat());
        question.setCreator(user.getId());

        questionMapper.insertQuestion(question);
        return true;
    }

    @Autowired
    UserMapper userMapper;

    public List<QuestionDto> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.findUserById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }
}
