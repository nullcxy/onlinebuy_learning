package com.cxy.message.dao;

import com.cxy.message.entity.MessageEventProcess;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@Mapper
public interface MessageEventProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageEventProcess record);

    int insertSelective(MessageEventProcess record);

    MessageEventProcess selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageEventProcess record);

    int updateByPrimaryKey(MessageEventProcess record);
}
