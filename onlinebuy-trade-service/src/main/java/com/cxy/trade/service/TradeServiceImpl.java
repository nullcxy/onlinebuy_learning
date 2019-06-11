package com.cxy.trade.service;

import com.cxy.common.enums.TradeStatus;
import com.cxy.common.resp.ApiResult;
import com.cxy.message.dao.MessageEventPublishMapper;
import com.cxy.message.entity.MessageEventPublish;
import com.cxy.product.dao.ProductSkuMapper;
import com.cxy.product.entity.ProductSku;
import com.cxy.trade.dao.TradeItemMapper;
import com.cxy.trade.dao.TradeMapper;
import com.cxy.trade.entity.StockReduce;
import com.cxy.trade.entity.Trade;
import com.cxy.trade.entity.TradeItem;
import com.cxy.trade.feign.KeyGeneratorServiceClient;
import com.cxy.trade.feign.StockServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TradeServiceImpl implements TradeService{

    @Autowired
    private KeyGeneratorServiceClient keyGeneratorServiceClient;

    @Autowired
    private StockServiceClient stockServiceClient;

    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private TradeItemMapper tradeItemMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MessageEventPublishMapper messageEventPublishMapper;

    /**
     *@Author JackWang [www.cxy.com]
     *@Date 2018/4/16 14:21
     *@Description 用户提单
     */

    @Transactional
    @Override
    public List<TradeItem> createOrder(List<TradeItem> tradeItemList) {
        //获取订单ID
        String orderNo = keyGeneratorServiceClient.keyGenerator();
        Long tradeNo = Long.parseLong(orderNo);
        Long userUUId = tradeItemList.get(0).getUserUuid();
        //检查库存
        List<StockReduce> stockReduceList = new ArrayList<>();
        tradeItemList.stream().forEach(
                param->{
                    StockReduce stockReduce = new StockReduce();
                    stockReduce.setOrderNo(tradeNo);
                    stockReduce.setSkuId(param.getSkuId());
                    stockReduce.setReduceCount(-param.getQuantity());

                    stockReduceList.add(stockReduce);
                }
        );
        ApiResult<Map<Long,Integer>> stockResult =  stockServiceClient.reduceStock(stockReduceList);
        //判断库存逻辑  插入订单
        Map<Long,Integer> stockResultMap = stockResult.getData();
        //查询相关SKU的属性
        List<ProductSku> skuResult = productSkuMapper.selectBySkuIdList(stockResultMap.keySet());

        //创建订单 和订单商品
        Trade trade = new Trade();
        trade.setTradeNo(tradeNo);
        trade.setStatus(TradeStatus.WAITING_PAY.getValue());
        trade.setUserUuid(userUUId);
        tradeMapper.insertSelective(trade);
        //拿到成功扣减库存的商品 插入订单商品表
        stockResultMap.keySet().stream().forEach(
                param->{
                    //扣库存失败的移除
                    if(stockResultMap.get(param)==-1){
                       TradeItem tradeItem =  tradeItemList.stream().filter(
                               item->item.getSkuId()==param
                       ).findFirst()
                        .get();
                        tradeItemList.remove(tradeItem);
                    }
                }
        );
        //计算商品价格等信息
        tradeItemList.stream().forEach(
                param->{
                    ProductSku sku = skuResult.stream().filter(
                            skuParam->param.getSkuId()==skuParam.getId()
                    ).findFirst()
                     .get();
                    param.setTradeNo(tradeNo);
                    param.setSkuImageUrl(sku.getImgUrl());
                    param.setSkuName(sku.getSkuName());
                    param.setCurrentPrice(sku.getSkuPrice());
                    param.setTotalPrice(sku.getSkuPrice().multiply(new BigDecimal(param.getQuantity())));

                    tradeItemMapper.insertSelective(param);
                }
        );


        //设置redis 订单号过期
        redisTemplate.opsForValue().set(tradeNo.toString(),tradeNo.toString(),20, TimeUnit.SECONDS);

        return tradeItemList;

    }

    @Override
    @Transactional
    public void payOrder(String out_trade_no) {

        Trade trade  = tradeMapper.selectByTradeNo(Long.valueOf(out_trade_no));
        if(trade.getStatus()==TradeStatus.WAITING_PAY.getValue()){
            trade.setStatus(TradeStatus.TRADE_PAIED.getValue());
//            trade.setPayment();
            trade.setPaymentTime(new Date());
            trade.setPaymenyType((byte)1);
            tradeMapper.updateByPrimaryKeySelective(trade);
            //插入消息表
            MessageEventPublish messageEventPublish = new MessageEventPublish();
            //请求ID服务拿一个ID 或者UUID 都行
//            messageEventPublish.setId();
            String json = "用户积分json";
            messageEventPublish.setPayload(json);
            messageEventPublish.setStatus((byte)1);
            messageEventPublishMapper.insertSelective(messageEventPublish);
        }
    }
}
