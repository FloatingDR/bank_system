package com.bank.system.model;

import lombok.Data;

import java.util.Date;

@Data
public class CardRegularInfo {
    private Integer id;

    private String bankCardId;

    private Double regularMoney;

    private Integer savingMonth;

    private Date savingDate;

    private Date stopDate;

}