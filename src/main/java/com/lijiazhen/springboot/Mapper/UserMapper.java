package com.lijiazhen.springboot.Mapper;

import com.lijiazhen.springboot.vo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select * from user where name = #{map.name} and password = #{map.password}")
    User login(@Param("map") Map<String,String> map);

    @Insert("insert into user values(#{user.id},#{user.name},#{user.password},#{user.age},#{user.phone})")
    void register(@Param("user") User user);

}
