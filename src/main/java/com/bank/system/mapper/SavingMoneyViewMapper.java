package com.bank.system.mapper;

import com.bank.system.model.SavingMoneyView;

import java.util.List;

public interface SavingMoneyViewMapper {

    /**
     * get by bank card id.
     * @param bankCardId
     * @return
     */
    List<SavingMoneyView> getByBankCardId(String bankCardId);
}