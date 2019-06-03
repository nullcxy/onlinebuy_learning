package com.cxy.common.exception;

import com.cxy.common.constants.Constants;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description: 用于缓存的user信息体
 */

public class MamaBuyException extends RuntimeException{

    private int statusCode = Constants.RESP_STATUS_INTERNAL_ERROR;

    public MamaBuyException(int statusCode,String message) {
        super(message);
        this.statusCode = statusCode;
    }
    public MamaBuyException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}