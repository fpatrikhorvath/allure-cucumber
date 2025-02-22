package com.cucumber.allure.config;

import org.springframework.context.annotation.Scope;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Scope(SCOPE_CUCUMBER_GLUE)
public class UserLayerConfig {
    private final String protocol;
    private final String ip;
    private final int    port;

    public UserLayerConfig(final String protocol,
                           final String ip,
                           final int port) {
        this.protocol = protocol;
        this.ip       = ip;
        this.port     = port;
    }

    public String getUrl() {
        return protocol + "://" + ip + ":" + port;
    }
}
