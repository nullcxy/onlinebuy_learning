package com.cxy.trade.dao;

import com.cxy.trade.entity.ShoppingCart;

public interface ShoppingCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
}