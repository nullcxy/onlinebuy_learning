package com.cxy.common.constants;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数
 */
@Component
@Data
public class Parameters {

    /*****redis config start*******/
    @Value("${spring.redis.url}")
    private String redisNode;
    @Value("${spring.redis.password}")
    private String redisAuth;
    /*****redis config end*******/

    /***zk config start ***/
    @Value("${zk.host}")
    private String zkHost;

    /***es config***/
    @Value("${es.host}")
    private String esHost;

    public String getRedisNode() {
        return redisNode;
    }

    public void setRedisNode(String redisNode) {
        this.redisNode = redisNode;
    }

    public String getRedisAuth() {
        return redisAuth;
    }

    public void setRedisAuth(String redisAuth) {
        this.redisAuth = redisAuth;
    }

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public String getEsHost() {
        return esHost;
    }

    public void setEsHost(String esHost) {
        this.esHost = esHost;
    }
}
