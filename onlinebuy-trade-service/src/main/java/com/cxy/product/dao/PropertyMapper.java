package com.cxy.product.dao;

import com.cxy.product.entity.Property;

public interface PropertyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Property record);

    int insertSelective(Property record);

    Property selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}