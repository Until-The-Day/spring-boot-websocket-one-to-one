package com.lee.app.config;

import com.lee.app.handler.CallHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallConfig {

    @Bean
    public CallHandler callHandler() {
        return new CallHandler();
    }

}
