package com.bank.system.model.require;

import com.bank.system.model.User;
import lombok.Data;

/**
 * @author taylor
 * @ClassName: UserAndRoleBean
 * @Description:
 * @date: 2019-05-20 13:26
 */
@Data
public class UserAndRoleBean {
    private User user;
    private String roleStyle;
}
