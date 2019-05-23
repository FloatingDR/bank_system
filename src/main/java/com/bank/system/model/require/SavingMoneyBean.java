package com.bank.system.model.require;

import lombok.Data;

/**
 * @author taylor
 * @ClassName: SavingMoneyBean
 * @Description:
 * @date: 2019-05-22 23:10
 */
@Data
public class SavingMoneyBean {
    private String bankCardId;
    private String password;
    private String savingType;
    private double money;
    private int regularMonth;
}
