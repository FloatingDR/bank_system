package com.bank.system.mapper;

import com.bank.system.model.UserCard;

public interface UserCardMapper {
    /**
     * delete by bank card id.
     * @param bankCardId
     * @return
     */
    int deleteByBankCardId(String bankCardId);

    int insert(UserCard record);

    int insertSelective(UserCard record);

    UserCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCard record);

    int updateByPrimaryKey(UserCard record);
}