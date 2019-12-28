package com.xcy.community.mapper;

import com.xcy.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id") //标识id为自增主键
    @Insert("insert into user (accound_id,name,token,gmt_creat,gmt_modified,avatar_url) value (#{accoundId},#{name},#{token},#{gmtCreat},#{gmtModified},#{avatarUrl})")
    public int insertUser(User user);

    @Select("select * from user where token=#{token}")
    public User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    public User findUserById(Integer creator);
}
