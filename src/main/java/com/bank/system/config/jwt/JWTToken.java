package com.bank.system.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author taylor
 * @ClassName: JWTToken
 * @Description: shiro用户密码的载体
 */
public class JWTToken implements AuthenticationToken {

    /**
     *  密钥
     */
    private String token;

    public JWTToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
