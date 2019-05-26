package com.bank.system.service.impl;

import com.bank.system.bean.ResponseBean;
import com.bank.system.bean.ResultCode;
import com.bank.system.bean.TransType;
import com.bank.system.mapper.BankCardInfoMapper;
import com.bank.system.mapper.CardRegularInfoMapper;
import com.bank.system.mapper.TransInfoMapper;
import com.bank.system.model.BankCardInfo;
import com.bank.system.model.CardRegularInfo;
import com.bank.system.model.TransInfo;
import com.bank.system.model.require.SavingMoneyBean;
import com.bank.system.model.require.TransBean;
import com.bank.system.service.AccessMoneyService;
import com.bank.system.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * @author taylor
 * @ClassName: AccessMoneyServiceImpl
 * @Description:
 * @date: 2019-05-22 13:58
 */
@Service
public class AccessMoneyServiceImpl implements AccessMoneyService {

    @Resource
    BankCardInfoMapper bankCardInfoMapper;
    @Resource
    TransInfoMapper transInfoMapper;
    @Resource
    CardRegularInfoMapper cardRegularInfoMapper;

    /**
     * 取款或转账
     *
     * @param transBean
     * @return 交易记录（含流水号）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean drawMoney(TransBean transBean) {

        //银行卡账号密码是否正确
        BankCardInfo DBbankCardInfo = bankCardInfoMapper.selectByBankCardId(transBean.getBankCardId());
        if (DBbankCardInfo == null) {
            return new ResponseBean(ResultCode.NOTFOUND, "not this card", "该银行卡不存在");
        }
        if (!MD5Util.verify(transBean.getPayPassword(), DBbankCardInfo.getPayPassword())) {
            return new ResponseBean(ResultCode.FORBIDDEN, "password err", "密码错误");
        }
        TransInfo guestTransInfo = transBean.getTransInfo();
        Date currentDate = new Date(System.currentTimeMillis());
        String bankCardId = transBean.getBankCardId();

        //log object
        TransInfo transInfo = new TransInfo();
        transInfo.setTradeDate(currentDate);
        transInfo.setDisburseCard(bankCardId);
        transInfo.setTransMoney(guestTransInfo.getTransMoney());

        String opType = guestTransInfo.getTransType();

        if (opType.equals(TransType.QUKUAN)) {
            //如果是活期取款
            if (DBbankCardInfo.getCurrentBalance() >= guestTransInfo.getTransMoney()) {
                BankCardInfo record = bankCardInfoMapper.selectByBankCardId(bankCardId);
                record.setBankCardId(bankCardId);
                record.setCurrentBalance(record.getCurrentBalance() - guestTransInfo.getTransMoney());
                bankCardInfoMapper.updateByBankCardIdSelective(record);

                //log
                transInfo.setTransType(TransType.QUKUAN);

            } else {
                return new ResponseBean(ResultCode.FORBIDDEN, "Lack of balance", "余额不足");
            }
        } else if (opType.equals(TransType.DINGQIQU)) {
            //如果是定期取款，必须到期才可
            List<CardRegularInfo> DBRegulars = cardRegularInfoMapper.getCardRegularInfos(bankCardId);
            if (DBRegulars.isEmpty()) {
                return new ResponseBean(ResultCode.NOTFOUND, "not this server", "该银行卡没有定期储蓄");
            }
            int flag = -2;
            for (CardRegularInfo cr : DBRegulars) {
                if (currentDate.compareTo(cr.getStopDate()) > 0) {

                    flag = -1;
                    if (guestTransInfo.getTransMoney() <= cr.getRegularMoney()) {
                        CardRegularInfo cd = new CardRegularInfo();
                        cd.setBankCardId(bankCardId);
                        cd.setRegularMoney(cr.getRegularMoney() - guestTransInfo.getTransMoney());
                        flag = cardRegularInfoMapper.updateByBankCardId(cd);
                        bankCardInfoMapper.updateRegularByCardId_Minus(bankCardId, guestTransInfo.getTransMoney());
                    }

                }
            }
            if (flag == -2) {
                return new ResponseBean(ResultCode.FORBIDDEN, "The card does not expire on a regular basis", "没有到定期时间的银行卡");
            }
            if (flag == -1) {
                return new ResponseBean(ResultCode.FORBIDDEN, "Lack of balance", "余额不足");
            }
            //log
            transInfo.setTransType(TransType.DINGQIQU);

        } else if (opType.equals(TransType.ZHUANZHANG)) {
            //如果是转账，只能转活期余额
            BankCardInfo b = bankCardInfoMapper.selectByBankCardId(guestTransInfo.getReceiptCard());
            if (b == null) {
                return new ResponseBean(ResultCode.NOTFOUND, "not this card", "对方银行卡号码不对");
            }
            BankCardInfo bankCardInfo = bankCardInfoMapper.selectByBankCardId(bankCardId);
            if (guestTransInfo.getTransMoney() > bankCardInfo.getCurrentBalance()) {
                return new ResponseBean(ResultCode.FORBIDDEN, "Lack of balance", "余额不足");
            }
            bankCardInfo.setCurrentBalance(bankCardInfo.getCurrentBalance() - guestTransInfo.getTransMoney());
            bankCardInfoMapper.updateByBankCardIdSelective(bankCardInfo);

            BankCardInfo receBank=bankCardInfoMapper.selectByBankCardId(guestTransInfo.getReceiptCard());
            receBank.setCurrentBalance(receBank.getCurrentBalance() + guestTransInfo.getTransMoney());
            bankCardInfoMapper.updateByBankCardIdSelective(receBank);

            //log
            transInfo.setReceiptCard(guestTransInfo.getReceiptCard());
            transInfo.setTransType(TransType.ZHUANZHANG);

        }
        transInfoMapper.insert(transInfo);
        return new ResponseBean(ResultCode.SUCCESS, "success", transInfo);
    }

    /**
     * 存款 活期/定期
     * @param savingMoneyBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean savingMoney(SavingMoneyBean savingMoneyBean) {
        //银行卡账号密码是否正确
        BankCardInfo DBbankCardInfo = bankCardInfoMapper.selectByBankCardId(savingMoneyBean.getBankCardId());
        if (DBbankCardInfo == null) {
            return new ResponseBean(ResultCode.NOTFOUND, "not this card", "该银行卡不存在");
        }
        if (!MD5Util.verify(savingMoneyBean.getPassword(), DBbankCardInfo.getPayPassword())) {
            return new ResponseBean(ResultCode.FORBIDDEN, "password err", "密码错误");
        }

        Date currentDate = new Date(System.currentTimeMillis());
        String bankCardId=savingMoneyBean.getBankCardId();

        //log object
        TransInfo transInfo = new TransInfo();
        transInfo.setTradeDate(currentDate);
        transInfo.setReceiptCard(bankCardId);
        transInfo.setTransMoney(savingMoneyBean.getMoney());

        //活期存款
        if(("活期").equals(savingMoneyBean.getSavingType())){
            transInfo.setTransType("活期存款");
            DBbankCardInfo.setCurrentBalance(DBbankCardInfo.getCurrentBalance()+savingMoneyBean.getMoney());
            if(bankCardInfoMapper.updateByBankCardIdSelective(DBbankCardInfo)<0){
                return new ResponseBean(ResultCode.FAIL, "saving fail", "活期存款失败");
            }
        }     //定期存款
        else{
            transInfo.setTransType("定期存款");

            Calendar calendar=Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(2,savingMoneyBean.getRegularMonth());
            // calendar的time转成java.util.Date格式日期
            java.util.Date utilDate = calendar.getTime();
            //java.util.Date日期转换成转成java.sql.Date格式
            Date stopDate =new Date(utilDate.getTime());

            CardRegularInfo record=new CardRegularInfo();
            record.setBankCardId(bankCardId);
            record.setRegularMoney(savingMoneyBean.getMoney());
            record.setSavingMonth(savingMoneyBean.getRegularMonth());
            record.setSavingDate(currentDate);
            record.setStopDate(stopDate);

            BankCardInfo bankCardInfo=bankCardInfoMapper.selectByBankCardId(bankCardId);
            BankCardInfo bkrecord=new BankCardInfo();
            bkrecord.setBankCardId(bankCardInfo.getBankCardId());
            bkrecord.setRegularBalance(bankCardInfo.getRegularBalance()+savingMoneyBean.getMoney());
            bankCardInfoMapper.updateByBankCardIdSelective(bkrecord);

            DBbankCardInfo.setRegularBalance(DBbankCardInfo.getRegularBalance()+savingMoneyBean.getMoney());

            if(cardRegularInfoMapper.insert(record)<0){
                return new ResponseBean(ResultCode.FAIL, "saving fail", "定期存款失败");
            }

        }
        transInfoMapper.insert(transInfo);
        return new ResponseBean(ResultCode.SUCCESS, "success",transInfo);
    }
}
