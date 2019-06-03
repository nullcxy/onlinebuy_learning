package com.cxy.user.service;

import com.cxy.common.constants.Constants;
import com.cxy.common.exception.MamaBuyException;
import com.cxy.user.dao.UserMapper;
import com.cxy.user.entity.User;
import com.cxy.user.entity.UserElement;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CuratorFramework zkClient;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    @Transactional
    public void registerUser(User user) throws Exception {

        InterProcessMutex lock=null;
        try {
            lock = new InterProcessMutex(zkClient, Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
            boolean retry = true;
            do{
                if (lock.acquire(3000, TimeUnit.MILLISECONDS)){
                    //查询重复用户
                    User repeatedUser = userMapper.selectByEmail(user.getEmail());
                    if(repeatedUser!=null){
                        throw  new MamaBuyException("用户邮箱重复");
                    }
                    //检查两次密码是否一致
                    user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
                    user.setNickname("码码购用户"+user.getEmail());
                    userMapper.insertSelective(user);
                }
                retry = false;
            }while (retry);

        } catch (Exception e) {
            log.error("用户注册异常",e);
            throw  e;
        } finally {
            if(null != lock){
                try {
                    lock.release();
                    log.info(user.getEmail()+Thread.currentThread().getName()+"释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public UserElement login(User user) {

        UserElement ue ;
        User existUser = userMapper.selectByEmail(user.getEmail());
        if(existUser==null){
            throw new MamaBuyException("用户不存在");
        }else {
            boolean result =cryptPasswordEncoder.matches(user.getPassword(),existUser.getPassword());
            if(!result){
                //密码错误
                throw new MamaBuyException("密码错误");
            }else{
                //验证通过 赋值ue
                ue = new UserElement();
                ue.setEmail(existUser.getEmail());
                ue.setUserId(existUser.getId());
                ue.setUuid(existUser.getUuid());
                ue.setNickname(existUser.getNickname());
            }
        }

        return ue;
    }
}

