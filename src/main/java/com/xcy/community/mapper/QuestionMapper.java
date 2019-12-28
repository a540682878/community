package com.xcy.community.mapper;

import com.xcy.community.model.Question;
import com.xcy.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;
import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_creat,gmt_modified,creator,tag,avatar_url) value (#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{tag},#{avatarUrl})")
    public int insertQuestion(Question question);

    @Select("select * from question")
    List<Question> list();

}
