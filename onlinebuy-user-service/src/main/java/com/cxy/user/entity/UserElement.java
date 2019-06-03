package com.cxy.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
@Data
public class UserElement implements Serializable {

    private Long userId;

    private Long uuid;

    private String email;

    private  String nickname;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
