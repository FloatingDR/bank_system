package com.bank.system.mapper;

import com.bank.system.model.CardRegularInfo;

import java.util.List;

public interface CardRegularInfoMapper {

    /**
     * 根据银行卡号删除记录
     * @param bankCardId
     * @return
     */
    int deleteByBankCardId(String bankCardId);

    /**
     * 根据银行卡号查询定期存款信息
     * @param bankCardId
     * @return
     */
    List<CardRegularInfo> getCardRegularInfos(String bankCardId);


    /**
     * 根据银行卡号更新
     * @param regularInfo
     * @return
     */
    int updateByBankCardId(CardRegularInfo regularInfo);

    int insert(CardRegularInfo record);

    int insertSelective(CardRegularInfo record);

    CardRegularInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardRegularInfo record);

    int updateByPrimaryKey(CardRegularInfo record);
}