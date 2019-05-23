package com.bank.system.service;

import com.bank.system.bean.ResponseBean;

/**
 * @author taylor
 * @ClassName: InfoQueryService
 * @Description:
 * @date: 2019-05-23 13:36
 */
public interface InfoQueryService {

    /**
     * 查询用户的银行账号信息
     * @param identityCardId
     * @return
     */
    ResponseBean queryBaseInfo(String identityCardId);

    /**
     * 查询用户的交易信息
     * @param bankCardId
     * @return
     */
    ResponseBean queryBillLog(String bankCardId);
}
