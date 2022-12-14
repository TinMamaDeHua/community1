package com.nowcoder.community.entity;

import java.util.Date;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-8 16:40
 */
public class LoginTicket {
    private int id;

    private int userId;

    private String ticket;
    /**
     * 凭证的状态
     * 0-有效; 1-无效;
     */
    private int status;
    /**
     * 凭证过期时间
     */
    private Date expired;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
