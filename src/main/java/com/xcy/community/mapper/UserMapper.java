package com.xcy.community.mapper;

import com.xcy.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (accound_id,name,token,gmt_creat,gmt_modified,avatar_url) value (#{accoundId},#{name},#{token},#{gmtCreat},#{gmtModified},#{avatarUrl})")
    public int insertUser(User user);

    @Select("select * from user where token=#{token}")
    public User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    public User findUserById(Integer creator);
}
