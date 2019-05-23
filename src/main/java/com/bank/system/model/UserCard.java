package com.bank.system.model;

import lombok.Data;

@Data
public class UserCard {
    private Integer id;

    private String identityCardId;

    private String bankCardId;
}