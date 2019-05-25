package com.bank.system.mapper;

import com.bank.system.model.UserCard;

import java.util.List;

public interface UserCardMapper {
    /**
     * delete by bank card id.
     * @param bankCardId
     * @return
     */
    int deleteByBankCardId(String bankCardId);

    /**
     * 得到该用户的所有银行卡号列表
     * @param identityCard
     * @return
     */
    List<String> getUserCards(String identityCard);

    int insert(UserCard record);

    int insertSelective(UserCard record);

    UserCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCard record);

    int updateByPrimaryKey(UserCard record);
}