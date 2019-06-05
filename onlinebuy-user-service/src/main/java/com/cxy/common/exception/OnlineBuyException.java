package com.cxy.common.exception;

import com.cxy.common.constants.Constants;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description: 用于缓存的user信息体
 */

public class OnlineBuyException extends RuntimeException{

    private int statusCode = Constants.RESP_STATUS_INTERNAL_ERROR;

    public OnlineBuyException(int statusCode,String message) {
        super(message);
        this.statusCode = statusCode;
    }
    public OnlineBuyException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}