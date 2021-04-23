package com.miaotech.collection.controller;


import com.miaotech.collection.common.BaseController;
import com.miaotech.collection.entity.User;
import com.miaotech.collection.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billchen
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/getUser")
    public User getUser(String username) {
        return userService.findByUsername(username);
    }

}
