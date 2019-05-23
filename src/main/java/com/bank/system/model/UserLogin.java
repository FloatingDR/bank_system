package com.bank.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin {
    private String identityCardId;

    private String loginPassword;

    private String roleStyle;
}