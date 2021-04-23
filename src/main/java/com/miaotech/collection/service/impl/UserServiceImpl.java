package com.miaotech.collection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miaotech.collection.entity.User;
import com.miaotech.collection.mapper.UserMapper;
import com.miaotech.collection.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billchen
 * @since 2021-04-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        return getOne(queryWrapper);
    }
}
