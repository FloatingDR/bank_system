package com.bank.system.model;

import lombok.Data;

import java.util.Date;

@Data
public class SavingMoneyView {
    private String identityCardId;

    private String username;

    private String address;

    private String telephone;

    private Date tradeDate;

    private String receiptCard;

    private String transType;

    private Double transMoney;

}