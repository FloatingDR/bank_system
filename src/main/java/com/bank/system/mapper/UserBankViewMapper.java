package com.bank.system.mapper;

import com.bank.system.model.UserBankView;

import java.util.List;
import java.util.Map;

/**
 * @author taylor
 */
public interface UserBankViewMapper {
    /**
     * 根据身份证查询名下所有银行卡号
     * @param IdCard
     * @return
     */
   List<String> getBankCardIdByIdCard(String IdCard);

    /**
     * 存储过程，是否可开户，同一身份证下有3张卡不许开户
     * @param sql
     * @return
     */
    String createAbelCallProc(Map<String,String> sql);

    /**
     * 查询用户在银行的账号信息
     * @param identityCardId
     * @return
     */
    List<UserBankView>getUserBankInfoByIdCard(String identityCardId);
}