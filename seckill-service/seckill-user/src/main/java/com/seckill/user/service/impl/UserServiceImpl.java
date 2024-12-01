package com.seckill.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.common.util.TimeUtil;
import com.seckill.user.dao.UserMapper;
import com.seckill.user.pojo.User;
import com.seckill.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.selectByPrimaryKey(username);
    }

    /**
     * User条件+分页查找
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findPage(User user,int pageNum, int pageSize) {
        // 分页
        PageHelper.startPage(pageNum, pageSize);
        // 执行搜索
        List<User> users = userMapper.selectAll();
        return new PageInfo<>(users);
    }

}
