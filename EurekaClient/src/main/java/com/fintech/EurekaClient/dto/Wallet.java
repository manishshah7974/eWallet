package com.fintech.EurekaClient.dto;

import com.fintech.EurekaClient.dto.User;

import java.math.BigDecimal;

public class Wallet {
    public Wallet(Long id, User user, BigDecimal balance, String type) {
        this.id = id;
        this.user = user;
        this.balance = balance;
        this.type = type;
    }
    public Wallet(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Long id;

    private User user;

    private BigDecimal balance;


    private String type;
}