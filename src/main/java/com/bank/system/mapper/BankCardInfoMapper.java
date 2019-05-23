package com.bank.system.mapper;

import com.bank.system.model.BankCardInfo;
import org.apache.ibatis.annotations.Param;


/**
 * @author taylor
 */
public interface BankCardInfoMapper {
    /**
     * 通过银行卡号查找银行卡信息
     * @param bankCardId
     * @return
     */
    BankCardInfo selectByBankCardId(String bankCardId);

    /**
     * 通过银行卡号删除
     * @param bankCardId
     * @return
     */
    int deleteByBankCardId(String bankCardId);

    /**
     * 全值插入
     * @param record
     * @return
     */
    int insert(BankCardInfo record);

    /**
     * 根据银行卡号更新记录
     * @param record
     * @return
     */
    int updateByBankCardIdSelective(BankCardInfo record);

    /**
     * 更新定期余额
     * @param bankCardId
     * @param regularBalance
     * @return
     */
    int updateRegularByCardId_Minus(@Param("bankCardId") String bankCardId,@Param("regularBalance") double regularBalance);
}