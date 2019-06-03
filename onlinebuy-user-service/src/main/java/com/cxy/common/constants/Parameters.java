package com.cxy.common.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@Component
@Data
public class Parameters {

    /*****redis config start*******/
    @Value("${redis.node}")
    private String redisNode;
    @Value("${redis.password}")
    private String redisAuth;
    /*****redis config end*******/

    /***zk config start ***/
    @Value("${zk.host}")
    private String zkHost;

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
}
