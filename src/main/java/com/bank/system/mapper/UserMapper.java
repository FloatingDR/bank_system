package com.bank.system.mapper;

import com.bank.system.model.User;

/**
 * @author taylor
 */
public interface UserMapper {

    /**
     * 根据身份证号查找用户信息
     * @param identityCardId
     * @return
     */
    User selectByIdCard(String identityCardId);

    /**
     * 根据身份证号删除用户信息
     * @param identityCardId
     * @return
     */
    int deleteByByIdCard(String identityCardId);

    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}