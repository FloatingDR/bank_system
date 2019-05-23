package com.bank.system.mapper;

import com.bank.system.model.DrawMoneyView;

import java.util.List;

public interface DrawMoneyViewMapper {

    /**
     * get by bank card id.
     * @param bankCardId
     * @return
     */
    List<DrawMoneyView> getByBankCardId(String bankCardId);

}