package com.seckill.user.dao;

import com.seckill.user.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("select * from tb_user where username = #{username}")
    User selectByPrimaryKey(String username);

    @Select("select * from tb_user")
    List<User> selectAll();

}
