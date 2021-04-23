package com.miaotech.collection.service;

import com.miaotech.collection.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author billchen
 * @since 2021-04-22
 */
public interface IUserService extends IService<User> {

    User findByUsername(String username);
}
