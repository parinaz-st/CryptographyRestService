package com.cryptography.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "certificate.sign")
public class CertificateFieldsConfig {
    private String path;
    private String password;
    private String alias;


    public CertificateFieldsConfig() {
    }

    public CertificateFieldsConfig(String path, String password, String alias) {
        this.path = path;
        this.password = password;
        this.alias = alias;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
