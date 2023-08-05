package com.gngsn.account.infra;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.gngsn.account")
public class FeignClientConfiguration {
}
