package com.bank.system.service.impl;

import com.bank.system.bean.ResponseBean;
import com.bank.system.bean.ResultCode;
import com.bank.system.config.jwt.JWTUtil;
import com.bank.system.mapper.BankCardInfoMapper;
import com.bank.system.mapper.CardRegularInfoMapper;
import com.bank.system.mapper.UserBankViewMapper;
import com.bank.system.mapper.UserCardMapper;
import com.bank.system.model.BankCardInfo;
import com.bank.system.model.CardRegularInfo;
import com.bank.system.service.BankCardService;
import com.bank.system.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author taylor
 * @ClassName: BankCardServiceImpl
 * @Description:
 * @date: 2019-05-20 17:41
 */
@Service
public class BankCardServiceImpl implements BankCardService {

    @Resource
    BankCardInfoMapper bankCardInfoMapper;
    @Resource
    UserBankViewMapper userBankViewMapper;
    @Resource
    CardRegularInfoMapper cardRegularInfoMapper;
    @Resource
    UserCardMapper userCardMapper;

    /**
     * 检测该银行卡是否属于你
     * @param Idcard
     * @param bankCard
     * @return
     */
    public boolean isYourCard(String Idcard,String bankCard){
        List<String> DBCards=userBankViewMapper.getBankCardIdByIdCard(Idcard);
        for(int i=0;i<DBCards.size();i++){
            if(DBCards.get(i).equals(bankCard)){
                return true;
            }
        }
        return false;
    }

    /**
     * 更改本人的银行卡密码
     * @param req
     * @param IdCard
     * @param bankCardId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public ResponseBean changePayPass(HttpServletRequest req, String IdCard, String bankCardId, String oldPassword, String newPassword) {
        String tokenIdCard= JWTUtil.getIdentityCardId(req.getHeader("Authorization"));
        if(!IdCard.equals(tokenIdCard)){
            return new ResponseBean(ResultCode.FORBIDDEN, "the account is not you login", "该账号和您登陆的账号不符");
        }
        if(!isYourCard(IdCard,bankCardId)){
            return new ResponseBean(ResultCode.FORBIDDEN, "this is not your card", "这不是您的银行卡");
        }
        BankCardInfo DBCardInfo=bankCardInfoMapper.selectByBankCardId(bankCardId);
        if (MD5Util.verify(oldPassword, DBCardInfo.getPayPassword())) {
            if (oldPassword.equals(newPassword)) {
                return new ResponseBean(ResultCode.FORBIDDEN, "password already", "密码未改变");
            }
            String newPass = MD5Util.md5(newPassword);
            DBCardInfo.setPayPassword(newPass);
            if (bankCardInfoMapper.updateByBankCardIdSelective(DBCardInfo) > 0) {
                return new ResponseBean(ResultCode.SUCCESS, "change success", "修改成功");
            }
            return new ResponseBean(ResultCode.SUCCESS, "change fail", "修改失败");
        }
        return new ResponseBean(ResultCode.FORBIDDEN, "password error", "原密码错误，不能修改");
    }

    /**
     * 注销银行卡 仅允许admin用户访问
     * @param bank_card_id
     * @param pay_password
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean deleteBankCard(String bank_card_id, String pay_password) {
        BankCardInfo bankCardInfo =bankCardInfoMapper.selectByBankCardId(bank_card_id);
        if(bankCardInfo==null){
            return new ResponseBean(ResultCode.NOTFOUND, "no this card", "该银行卡不存在");
        }
        if(MD5Util.verify(pay_password,bankCardInfo.getPayPassword())){
            if(bankCardInfo.getCurrentBalance()+bankCardInfo.getRegularBalance()>10){
                return new ResponseBean(ResultCode.FORBIDDEN, "your balance is no empty", "银行卡账号余额大于10元，不能注销");
            }
            //清除银行卡的定期存款信息
            List<CardRegularInfo> cardRegularList=cardRegularInfoMapper.getCardRegularInfos(bank_card_id);
            if(!cardRegularList.isEmpty()){
                cardRegularInfoMapper.deleteByBankCardId(bank_card_id);
            }

            //清除银行卡的用户关系
            userCardMapper.deleteByBankCardId(bank_card_id);

            //注销银行卡
            if(bankCardInfoMapper.deleteByBankCardId(bank_card_id)>0){
                return new ResponseBean(ResultCode.SUCCESS, "delete success", "注销成功");
            }
            return new ResponseBean(ResultCode.FAIL, "delete fail", "注销失败");

        }
        return new ResponseBean(ResultCode.FORBIDDEN, "password error", "密码错误，不允许注销");
    }

    /**
     * 根据身份证号查找银行卡列表
     * @param identityCardId
     * @return
     */
    @Override
    public ResponseBean getUserCards(String identityCardId) {
        List<String> bankCards=userCardMapper.getUserCards(identityCardId);
        if(bankCards.isEmpty()){
            return new ResponseBean(ResultCode.NOTFOUND, "you are no bank cards", "你没有银行卡");
        }
        return new ResponseBean(ResultCode.SUCCESS, "this is your bank cards", bankCards);
    }

}
