package com.bank.system.service;

import com.bank.system.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author taylor
 * @ClassName: BankCardService
 * @Description:
 * @date: 2019-05-20 17:41
 */
public interface BankCardService {

    /**
     * 更改银行卡密码
     * @param req
     * @param IdCard
     * @param bankCardId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    ResponseBean changePayPass(HttpServletRequest req, String IdCard, String bankCardId, String oldPassword, String newPassword);

    /**
     * 注销银行卡账号
     * @param bank_card_id
     * @param pay_password
     * @return
     */
    ResponseBean deleteBankCard(String bank_card_id,String pay_password);

    /**
     * 根据身份证号查找银行卡列表
     * @param identityCardId
     * @return
     */
    ResponseBean getUserCards(String identityCardId);
}
