package com.cxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Auther: cxy
 * @Date: 2019/05/30
 * @Description:
 */
@SpringBootApplication
@EnableEurekaServer
public class OnlineBuyServiceRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineBuyServiceRegistryApplication.class, args);
    }
}
