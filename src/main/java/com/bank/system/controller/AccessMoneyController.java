package com.bank.system.controller;

import com.bank.system.bean.ResponseBean;
import com.bank.system.model.require.SavingMoneyBean;
import com.bank.system.model.require.TransBean;
import com.bank.system.service.AccessMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author taylor
 * @ClassName: AccessMoneyController
 * @Description:
 * @date: 2019-05-22 13:59
 */
@RestController
@RequestMapping("/api/access_money")
@CrossOrigin
public class AccessMoneyController {

    @Autowired
    AccessMoneyService accessMoneyService;

    /**
     * 取款转账
     * @param transBean
     * @return
     */
    @PostMapping("/draw_money")
    public ResponseBean drawMoney(@RequestBody TransBean transBean){
        return accessMoneyService.drawMoney(transBean);
    }

    /**
     * 存款 活期/定期
     * @param smb
     * @return
     */
    @PostMapping("/saving_money")
    public ResponseBean savingMoney(@RequestBody SavingMoneyBean smb){
        return accessMoneyService.savingMoney(smb);
    }
}
