package com.cxy.trade.dao;

import com.cxy.trade.entity.Trade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);

    Trade selectByTradeNo(Long tradeNo);
}