package com.bank.system.service;

import com.bank.system.bean.ResponseBean;
import com.bank.system.model.require.SavingMoneyBean;
import com.bank.system.model.require.TransBean;

/**
 * @author taylor
 * @ClassName: AccessMoneyService
 * @Description:
 * @date: 2019-05-22 13:58
 */
public interface AccessMoneyService {

    /**
     * 取款或转账
     * @param transBean
     * @return
     */
    ResponseBean drawMoney(TransBean transBean);

    /**
     * 存款 活期/定期
     * @param savingMoneyBean
     * @return
     */
    ResponseBean savingMoney(SavingMoneyBean savingMoneyBean);

}
