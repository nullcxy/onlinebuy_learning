package com.cxy.user.dao;

import com.cxy.user.entity.UserBonusPoints;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@Mapper
public interface UserBonusPointsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBonusPoints record);

    int insertSelective(UserBonusPoints record);

    UserBonusPoints selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBonusPoints record);

    int updateByPrimaryKey(UserBonusPoints record);
}
