package com.bank.system.mapper;

import com.bank.system.model.UserLogin;

public interface UserLoginMapper {

    /**
     * 通过身份证查询
     * @param identityCardId
     * @return
     */
    UserLogin selectByIdCard(String identityCardId);

    /**
     * 通过身份证删除
     * @param identityCardId
     * @return
     */
    int deleteByIdCard(String identityCardId);

    /**
     * 全值插入
     * @param record
     * @return
     */
    int insert(UserLogin record);

    /**
     * 半值插入
     * @param record
     * @return
     */
    int insertSelective(UserLogin record);

    /**
     * 半值更新
     * @param record
     * @return
     */
    int updateByIdCardSelective(UserLogin record);
}