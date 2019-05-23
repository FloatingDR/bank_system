package com.bank.system.service;

import com.bank.system.bean.ResponseBean;
import com.bank.system.model.UserLogin;
import com.bank.system.model.require.RegisterBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author taylor
 * @ClassName: AuthService
 * @Description:
 * @date: 2019-05-20 13:18
 */
public interface AuthService {

    /**
     * 用户登陆
     * @param userLogin 登陆信息
     * @return ResponseBean 404，403，200
     */
    ResponseBean login(UserLogin userLogin);

    /**
     * 更改登陆密码
     * @param idCard
     * @param oldLoginPass
     * @param newLoginPass
     * @return
     */
    ResponseBean changeLoginPassword(String idCard,String oldLoginPass,String newLoginPass);

    /**
     * 开户
     * @param registerBean
     * @return 银行卡号
     */
    ResponseBean registerAccount(RegisterBean registerBean);
}
