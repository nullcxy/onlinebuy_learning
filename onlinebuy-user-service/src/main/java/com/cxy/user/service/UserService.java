package com.cxy.user.service;

import com.cxy.user.entity.User;
import com.cxy.user.entity.UserElement;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
public interface UserService {
    User findUserById(Long id);

    void registerUser(User user) throws Exception;

    UserElement login(User user);
}
