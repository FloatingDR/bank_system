package com.bank.system.model.require;

import lombok.Data;

/**
 * @author taylor
 * @ClassName: ChangePassBean
 * @Description:
 * @date: 2019-05-20 14:54
 */
@Data
public class ChangePassBean {
    private String identityCardId;
    private String bankCardId;
    private String oldPassword;
    private String newPassword;
}
