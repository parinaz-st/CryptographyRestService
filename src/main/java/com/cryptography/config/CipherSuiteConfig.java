package com.cryptography.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CipherSuiteConfig {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                    @Override
                    public void customize(Connector connector) {
                        AbstractHttp11Protocol<?> httpHandler = ((AbstractHttp11Protocol<?>) connector.getProtocolHandler());
                        httpHandler.setUseServerCipherSuitesOrder(true);
                        httpHandler.setSSLProtocol("TLSv1.2");
                        httpHandler.setSSLHonorCipherOrder(true);
                        httpHandler.setCiphers("TLS_RSA_WITH_AES_256_CBC_SHA256," +
                                "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384," +
                                "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384," +
                                "TLS_RSA_WITH_AES_256_GCM_SHA384," +
                                "TLS_RSA_WITH_AES_128_GCM_SHA256," +
                                "TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256," +
                                "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256");
                    }
                });
            }
        };
    }
}
