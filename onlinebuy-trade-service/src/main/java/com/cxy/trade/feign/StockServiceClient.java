package com.cxy.trade.feign;

import com.cxy.common.resp.ApiResult;
import com.cxy.trade.entity.StockReduce;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


@FeignClient(name = "stock-service",fallback = StockServiceFallback.class)
public interface StockServiceClient {


    @RequestMapping(value = "/stock/reduce",method = RequestMethod.POST)
    ApiResult<Map<Long,Integer>> reduceStock(@RequestBody List<StockReduce> stockReduceList);
}
