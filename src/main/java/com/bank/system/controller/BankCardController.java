package com.bank.system.controller;

import com.bank.system.bean.ResponseBean;
import com.bank.system.model.BankCardInfo;
import com.bank.system.model.require.ChangePassBean;
import com.bank.system.service.BankCardService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author taylor
 * @ClassName: BankCardController
 * @Description:
 * @date: 2019-05-20 18:03
 */
@RestController
@RequestMapping("/api/bank_card")
@CrossOrigin
public class BankCardController {

    @Autowired
    BankCardService bankCardService;

    /**
     * 修改用户的银行卡密码 登陆可访问 只能修改已登陆账号的
     * @param req
     * @param changePassBean
     * @return ResponseBean
     * @date 2019-5-20
     */
    @PostMapping("/change_bankcard_password")
    public ResponseBean login(HttpServletRequest req,@RequestBody ChangePassBean changePassBean) {
        return bankCardService.changePayPass(req,changePassBean.getIdentityCardId(),changePassBean.getBankCardId(),
                changePassBean.getOldPassword(),changePassBean.getNewPassword());
    }

    @PostMapping("/delete_bankcard")
    @RequiresRoles("admin")
    public ResponseBean deleteBankCard(@RequestBody BankCardInfo bankCardInfo){
        return bankCardService.deleteBankCard(bankCardInfo.getBankCardId(),bankCardInfo.getPayPassword());
    }

}
