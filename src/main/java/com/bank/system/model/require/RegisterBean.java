package com.bank.system.model.require;

import lombok.Data;

/**
 * @author taylor
 * @ClassName: RegisterBean
 * @Description:
 * @date: 2019-05-21 00:10
 */
@Data
public class RegisterBean {
    private String identityCardId;
    private String username;
    private String address;
    private String telephone;
    private String loginPassword;
    private String payPassword;
    private double openMoney;
}
