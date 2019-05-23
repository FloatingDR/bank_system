package com.bank.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author taylor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCardInfo {

    private String bankCardId;

    private String payPassword;

    private Date openDate;

    private double openMoney;

    private Double currentBalance;

    private Double regularBalance;
}