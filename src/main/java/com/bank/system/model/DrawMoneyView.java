package com.bank.system.model;

import lombok.Data;

import java.util.Date;

@Data
public class DrawMoneyView {
    private String identityCardId;

    private String username;

    private String address;

    private String telephone;

    private String bankCardId;

    private Date tradeDate;

    private String disburseCard;

    private String receiptCard;

    private String transType;

    private Double transMoney;
}