package com.bank.system.service.impl;

import com.bank.system.bean.ResponseBean;
import com.bank.system.bean.ResultCode;
import com.bank.system.config.jwt.JWTUtil;
import com.bank.system.mapper.*;
import com.bank.system.model.BankCardInfo;
import com.bank.system.model.User;
import com.bank.system.model.UserCard;
import com.bank.system.model.UserLogin;
import com.bank.system.model.require.RegisterBean;
import com.bank.system.model.require.UserAndRoleBean;
import com.bank.system.service.AuthService;
import com.bank.system.utils.CreateCard;
import com.bank.system.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author taylor
 * @ClassName: AuthServiceImpl
 * @Description:
 * @date: 2019-05-20 13:19
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    UserCardMapper userCardMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    UserLoginMapper loginMapper;
    @Resource
    BankCardInfoMapper bankCardInfoMapper;
    @Resource
    UserBankViewMapper viewMapper;

    /**
     * 用户登陆
     *
     * @param userLogin 登陆信息
     * @return ResponseBean 404，403，200
     */
    @Override
    public ResponseBean login(UserLogin userLogin) {
        //判断该账号是否存在？
        UserLogin DBloginInfo = loginMapper.selectByIdCard(userLogin.getIdentityCardId());
        //不存在
        if (DBloginInfo == null) {
            return new ResponseBean(ResultCode.NOTFOUND, "no this auth", "没有该用户");
        }
        //存在
        String passwordEncoded = DBloginInfo.getLoginPassword();
        if (MD5Util.verify(userLogin.getLoginPassword(), passwordEncoded)) {
            String token = JWTUtil.sign(userLogin.getIdentityCardId(), passwordEncoded);

            UserAndRoleBean userAndRoleBean = new UserAndRoleBean();
            userAndRoleBean.setUser(userMapper.selectByIdCard(DBloginInfo.getIdentityCardId()));
            userAndRoleBean.setRoleStyle(DBloginInfo.getRoleStyle());

            return new ResponseBean(ResultCode.SUCCESS, token, userAndRoleBean);
        }
        return new ResponseBean(ResultCode.FORBIDDEN, "password error", "密码错误");
    }

    /**
     * 更改登陆密码
     *
     * @param idCard 身份证号
     * @param oldLoginPass 旧密码
     * @param newLoginPass 新密码
     * @return
     */
    @Override
    public ResponseBean changeLoginPassword(String idCard, String oldLoginPass, String newLoginPass) {
        UserLogin DBUserLogin = loginMapper.selectByIdCard(idCard);
        if (DBUserLogin == null) {
            return new ResponseBean(ResultCode.FORBIDDEN, "no this user", "没有该用户");
        }
        if (MD5Util.verify(oldLoginPass, DBUserLogin.getLoginPassword())) {
            if (oldLoginPass.equals(newLoginPass)) {
                return new ResponseBean(ResultCode.FORBIDDEN, "password already", "密码未改变");
            }
            String newPass = MD5Util.md5(newLoginPass);
            DBUserLogin.setLoginPassword(newPass);
            if (loginMapper.updateByIdCardSelective(DBUserLogin) > 0) {
                return new ResponseBean(ResultCode.SUCCESS, "change success", "修改成功");
            }
            return new ResponseBean(ResultCode.SUCCESS, "change fail", "修改失败");
        }
        return new ResponseBean(ResultCode.FORBIDDEN, "password error", "原密码错误，不能修改");
    }

    /**
     * 开户
     * @param reg
     * @return 银行卡号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean registerAccount(RegisterBean reg) {

        //如果该身份证已有三张卡，不允许开户
        Map<String,String> sql=new HashMap<>(2);
        sql.put("identityCardId",reg.getIdentityCardId());
        sql.put("result","");
        viewMapper.createAbelCallProc(sql);
        if(!("ok").equals(sql.get("result"))){
            return new ResponseBean(ResultCode.FORBIDDEN, "You have too many bank CARDS", "你有太多银行卡");
        }

        //随机生成银行卡号
        String bankCardId;
        do{
            bankCardId=CreateCard.createBankCardId();
        }while (bankCardInfoMapper.selectByBankCardId(bankCardId)!=null);

        //插入银行卡信息
        Date date=new Date(System.currentTimeMillis());
        BankCardInfo bankCardInfo=new BankCardInfo(bankCardId,MD5Util.md5(reg.getPayPassword()),
                date,reg.getOpenMoney(),reg.getOpenMoney(),0.0);
        System.out.println(bankCardId);
        bankCardInfoMapper.insert(bankCardInfo);

        //如果是第一次办卡，插入用户信息
        User u=userMapper.selectByIdCard(reg.getIdentityCardId());
        if(u==null){
            //插入用户信息
            User user=new User(reg.getIdentityCardId(),reg.getUsername(),reg.getAddress(),reg.getTelephone());
            System.out.println(user);
            userMapper.insert(user);

            //插入登陆信息
            UserLogin userLogin=new UserLogin(reg.getIdentityCardId(),MD5Util.md5(reg.getLoginPassword()),"guest");
            System.out.println(userLogin);
            loginMapper.insert(userLogin);
        }

        //插入用户-银行卡关系表信息
        UserCard userCard=new UserCard();
        userCard.setIdentityCardId(reg.getIdentityCardId());
        userCard.setBankCardId(bankCardId);
        System.out.println(userCard);
        if(userCardMapper.insert(userCard)>0){
            return new ResponseBean(ResultCode.SUCCESS, "this is your bank card", bankCardId);
        }
        return new ResponseBean(ResultCode.FAIL, "create account fail", "开户失败");
    }


}
