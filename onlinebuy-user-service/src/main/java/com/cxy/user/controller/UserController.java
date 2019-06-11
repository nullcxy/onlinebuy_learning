package com.cxy.user.controller;

import com.cxy.common.constants.Constants;
import com.cxy.common.resp.ApiResult;
import com.cxy.user.entity.User;
import com.cxy.user.entity.UserElement;
import com.cxy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/register")
    public ApiResult register (@RequestBody @Valid User user) throws Exception {
        userService.registerUser(user);
        return new ApiResult(Constants.RESP_STATUS_OK,"注册成功");
    }

    /**
     * 用户登录
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ApiResult login(HttpSession session, @RequestBody @Valid User user){
        ApiResult<UserElement> result = new ApiResult<>(Constants.RESP_STATUS_OK,"登录成功");

        UserElement ue= userService.login(user);
        if(ue!=null){
            if(session.getAttribute(Constants.REQUEST_USER_SESSION)==null){
                session.setAttribute(Constants.REQUEST_USER_SESSION,ue);
            }
            result.setData(ue);
        }
        return result;
    }

    @RequestMapping("/info")
    public ApiResult<UserElement> getUserInfo(HttpSession session)
    {
        ApiResult<UserElement> result = new ApiResult<>(Constants.RESP_STATUS_OK,"获取用户信息成功");
        UserElement ue = (UserElement)session.getAttribute(Constants.REQUEST_USER_SESSION);
        if(ue==null){
            return new ApiResult<>(Constants.RESP_STATUS_BADREQUEST,"请求头错误");
        }else {
            result.setData(ue);
        }

        return result;
    }
}

