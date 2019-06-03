package com.cxy.user.dao;

import com.cxy.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmail(String email);
}
