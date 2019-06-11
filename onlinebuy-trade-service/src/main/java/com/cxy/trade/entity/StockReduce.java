package com.cxy.trade.entity;

import lombok.Data;

@Data
public class StockReduce {

    private Long orderNo;

    private Long skuId;

    private Integer reduceCount; //正数为增加/释放库存  负数为扣减

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getReduceCount() {
        return reduceCount;
    }

    public void setReduceCount(Integer reduceCount) {
        this.reduceCount = reduceCount;
    }
}
