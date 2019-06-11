package com.cxy.product.dao;

import com.cxy.product.entity.PropertyOption;

public interface PropertyOptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PropertyOption record);

    int insertSelective(PropertyOption record);

    PropertyOption selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PropertyOption record);

    int updateByPrimaryKey(PropertyOption record);
}