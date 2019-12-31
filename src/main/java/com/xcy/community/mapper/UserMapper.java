package com.xcy.community.mapper;

import com.xcy.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    //添加user
    @Options(useGeneratedKeys = true,keyProperty = "id") //标识id为自增主键
    @Insert("insert into user (accound_id,name,token,gmt_creat,gmt_modified,avatar_url) value (#{accoundId},#{name},#{token},#{gmtCreat},#{gmtModified},#{avatarUrl})")
    public int insertUser(User user);

    //通过token获取user信息
    @Select("select * from user where token=#{token}")
    public User findByToken(@Param("token") String token);

    //通过AccoundId获取user信息
    @Select("select * from user where accound_id=#{accoundId}")
    public User findUserByAccoundId(Long accoundId);

    //更新user
    @Update("update user set name=#{name}, token=#{token}, gmt_modified=#{gmtModified}, avatar_url=#{avatarUrl} where accound_id=#{accoundId}")
    public void updateUser(User dbuser);
}
