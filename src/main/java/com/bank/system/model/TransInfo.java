package com.bank.system.model;

import lombok.Data;

import java.util.Date;

@Data
public class TransInfo {
    private Integer id;

    private Date tradeDate;

    private String disburseCard;

    private String receiptCard;

    private String transType;

    private Double transMoney;
}