package com.cxy.user.entity;

/**
 * @Auther: cxy
 * @Date: 2019/6/3
 * @Description:
 */
public class UserBonusPoints {
    private Integer id;

    private Long userUuid;

    private Integer points;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Long userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
