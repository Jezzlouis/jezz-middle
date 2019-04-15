package com.jezz.sso.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class SSOAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 客户端一些配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("sso-client1")
                .secret("client1secrect1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all","read","write")
                .autoApprove(true)
                .and()
                .withClient("sso-client2")
                .secret("client2secrect2")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all","read","write")
                .autoApprove(true);
    }

}
