package com.bank.system.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserBankView {
    private String identityCardId;

    private String username;

    private String address;

    private String telephone;

    private String bankCardId;

    private Date openDate;

    private String openMoney;

    private Double currentBalance;

    private Double regularBalance;
}