package com.bank.system.controller;

import com.bank.system.bean.ResponseBean;
import com.bank.system.service.InfoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author taylor
 * @ClassName: InfoQueryController
 * @Description:
 * @date: 2019-05-23 16:43
 */
@RestController
@RequestMapping("/api/query_info")
@CrossOrigin
public class InfoQueryController {

    @Autowired
    InfoQueryService infoQueryService;

    @GetMapping("/user_base_info/{identityCardId}")
    public ResponseBean queryBaseInfo(@PathVariable String identityCardId){
        return infoQueryService.queryBaseInfo(identityCardId);
    }

    @GetMapping("/bill_log/{bankCardId}")
    public ResponseBean queryBillLog(@PathVariable String bankCardId){
        return infoQueryService.queryBillLog(bankCardId);
    }
}
