package com.bank.system.service.impl;

import com.bank.system.bean.ResponseBean;
import com.bank.system.bean.ResultCode;
import com.bank.system.mapper.DrawMoneyViewMapper;
import com.bank.system.mapper.SavingMoneyViewMapper;
import com.bank.system.mapper.UserBankViewMapper;
import com.bank.system.model.DrawMoneyView;
import com.bank.system.model.SavingMoneyView;
import com.bank.system.model.UserBankView;
import com.bank.system.model.require.TransLog;
import com.bank.system.service.InfoQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author taylor
 * @ClassName: InfoQueryServiceImpl
 * @Description:
 * @date: 2019-05-23 13:36
 */
@Service
public class InfoQueryServiceImpl implements InfoQueryService {

    @Resource
    UserBankViewMapper userBankViewMapper;
    @Resource
    SavingMoneyViewMapper savingMoneyViewMapper;
    @Resource
    DrawMoneyViewMapper drawMoneyViewMapper;

    /**
     * 查询用户的银行账号信息
     * @param identityCardId
     * @return
     */
    @Override
    public ResponseBean queryBaseInfo(String identityCardId) {
        List<UserBankView> bankViews=userBankViewMapper.getUserBankInfoByIdCard(identityCardId);
        if(bankViews.isEmpty()){
            return new ResponseBean(ResultCode.NOTFOUND,"not found","没有该用户信息");
        }
        return new ResponseBean(ResultCode.SUCCESS,"success",bankViews);
    }

    /**
     * 查询用户的交易信息
     * @param bankCardId
     * @return
     */
    @Override
    public ResponseBean queryBillLog(String bankCardId) {
        TransLog transLog=new TransLog();
        List<DrawMoneyView> drawMoneyViews=drawMoneyViewMapper.getByBankCardId(bankCardId);
        List<SavingMoneyView> savingMoneyViews=savingMoneyViewMapper.getByBankCardId(bankCardId);
        if(drawMoneyViews.isEmpty() && savingMoneyViews.isEmpty()){
            return new ResponseBean(ResultCode.NOTFOUND,"not found","最近没有交易记录");
        }
        transLog.setDrawLong(drawMoneyViews);
        transLog.setSavingLog(savingMoneyViews);
        return new ResponseBean(ResultCode.SUCCESS,"success",transLog);
    }
}
