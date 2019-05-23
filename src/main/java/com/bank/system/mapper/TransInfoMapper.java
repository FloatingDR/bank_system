package com.bank.system.mapper;

import com.bank.system.model.TransInfo;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;

public interface TransInfoMapper {

    /**
     * 根据银行卡号和日期查找一条记录
     * @param bankCardId
     * @param tradeDate
     * @return
     */
    TransInfo selectByBankIdAndDate(@Param("bankCardId")String bankCardId,
                                    @Param("tradeDate") Date tradeDate);

    int deleteByPrimaryKey(Integer id);

    int insert(TransInfo record);

    int insertSelective(TransInfo record);

    TransInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransInfo record);

    int updateByPrimaryKey(TransInfo record);
}