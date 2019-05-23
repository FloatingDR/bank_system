package com.bank.system.controller;

import com.bank.system.bean.ResponseBean;
import com.bank.system.model.UserLogin;
import com.bank.system.model.require.ChangePassBean;
import com.bank.system.model.require.RegisterBean;
import com.bank.system.service.AuthService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author taylor
 * @ClassName: AuthController
 * @Description:
 * @date: 2019-05-20 11:38
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * 测试接口 登陆可访问
     *
     * @return "部署成功了哈!"
     * @date 2019-5-20
     */
    @GetMapping("/hello")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "guest"})
    public String hello() {
        return "部署成功了哈!";
    }

    /**
     * 用户登陆 所有人都可以访问
     *
     * @param userLogin 用户登陆信息
     * @return ResponseBean
     * @date 2019-5-20
     */
    @PostMapping("/login")
    public ResponseBean login(@RequestBody UserLogin userLogin) {
        return authService.login(userLogin);
    }

    /**
     * 更改密码 所有人可访问
     * @param bean
     * @date 2019-5-20
     */
    @PostMapping("/change_login_password")
    public ResponseBean changePassword(@RequestBody ChangePassBean bean) {
        return authService.changeLoginPassword(bean.getIdentityCardId(),bean.getOldPassword(),bean.getNewPassword());
    }

    /**
     * 开户，仅admin可访问
     *
     * @param bean
     * @date 2019-5-20
     */
    @PostMapping("/register_account")
    @RequiresRoles("admin")
    public ResponseBean registerAccount(@RequestBody RegisterBean bean) {
        return authService.registerAccount(bean);
    }

}
