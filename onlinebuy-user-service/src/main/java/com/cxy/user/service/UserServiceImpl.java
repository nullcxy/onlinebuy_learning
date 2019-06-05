package com.cxy.user.service;

import com.cxy.common.constants.Constants;
import com.cxy.common.exception.OnlineBuyException;
import com.cxy.user.dao.UserMapper;
import com.cxy.user.entity.User;
import com.cxy.user.entity.UserElement;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 1.单表：悲观锁 select *  .. for update
     * 2.单表：操作表设置e-mail唯一，插入重复报DuplicateKeyException异常，检测出异常即显示重复注册
     * 3.分表：分布式锁，排序
     * @param user
     * @throws Exception
     */
    @Override
    @Transactional
    public void registerUser(User user) throws Exception {
        InterProcessMutex lock=null;
        try {
            lock = new InterProcessMutex(curatorFramework, Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
            boolean retry = true;
            do{
                if (lock.acquire(3000, TimeUnit.MILLISECONDS)){
                    //查询重复用户
                    User repeatedUser = userMapper.selectByEmail(user.getEmail());
                    if(repeatedUser!=null){
                        throw  new OnlineBuyException("用户邮箱重复");
                    }
                    //检查两次密码是否一致
                    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
            throw new OnlineBuyException("用户不存在");
        }else {
            boolean result =bCryptPasswordEncoder.matches(user.getPassword(),existUser.getPassword());
            if(!result){
                //密码错误
                throw new OnlineBuyException("密码错误");
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

