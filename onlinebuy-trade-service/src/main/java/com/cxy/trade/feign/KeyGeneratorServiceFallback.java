package com.cxy.trade.feign;


public class KeyGeneratorServiceFallback implements KeyGeneratorServiceClient{
    @Override
    public String keyGenerator() {
        return null;
    }
}
