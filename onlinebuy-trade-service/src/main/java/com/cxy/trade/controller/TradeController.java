package com.cxy.trade.controller;

import com.cxy.common.constants.Constants;
import com.cxy.common.resp.ApiResult;
import com.cxy.trade.entity.TradeItem;
import com.cxy.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("trade")
public class TradeController {


    @Autowired
    @Qualifier("tradeServiceImpl")
    private TradeService tradeService;

    @RequestMapping("/order")
    public ApiResult<List<TradeItem>> createOrder(@RequestBody List<TradeItem> tradeItemList){

        ApiResult<List<TradeItem>> result = new ApiResult(Constants.RESP_STATUS_OK,"订单提交成功");

        List<TradeItem> tradeItemSuccResult =tradeService.createOrder(tradeItemList);
        result.setData(tradeItemSuccResult);

        return  result;
    }
}
