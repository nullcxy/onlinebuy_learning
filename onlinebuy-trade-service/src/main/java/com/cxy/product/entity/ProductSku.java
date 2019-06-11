package com.cxy.product.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductSku {

    private Long id;

    private Long spuId;

    private String skuName;

    private BigDecimal skuPrice;

    private String imgUrl;

    private byte enableFlag;

    private byte status;

    private List<SkuPropertyOption> skuPropertyOptions;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public byte getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(byte enableFlag) {
        this.enableFlag = enableFlag;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public List<SkuPropertyOption> getSkuPropertyOptions() {
        return skuPropertyOptions;
    }

    public void setSkuPropertyOptions(List<SkuPropertyOption> skuPropertyOptions) {
        this.skuPropertyOptions = skuPropertyOptions;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}