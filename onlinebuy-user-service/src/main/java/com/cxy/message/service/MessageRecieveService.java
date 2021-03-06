package com.cxy.message.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@EnableBinding(Sink.class)
public class MessageRecieveService {

    @StreamListener(Sink.INPUT)
    public void recieveMsg(Object payload){
        System.out.println(payload);
    }
}
