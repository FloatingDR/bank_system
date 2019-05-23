package com.bank.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author taylor
 */
@Data
@AllArgsConstructor
public class User {
    private String identityCardId;

    private String username;

    private String address;

    private String telephone;
}