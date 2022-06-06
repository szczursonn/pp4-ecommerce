package pl.mszcz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// https://www.baeldung.com/configuration-properties-in-spring-boot#1-spring-boot-22
@ConfigurationProperties
@ConfigurationPropertiesScan
public class AppProperties {
    private String payUClientId;
    private String payUClientSecret;
    private String payUPosId;

    public String getPayUClientId() {
        return this.payUClientId;
    }

    public void setPayUClientId(String payUClientId) {
        this.payUClientId = payUClientId;
    }

    public String getPayUClientSecret() {
        return this.payUClientSecret;
    }

    public void setPayUClientSecret(String payUClientSecret) {
        this.payUClientSecret = payUClientSecret;
    }

    public String getPayUPosId() {
        return this.payUPosId;
    }

    public void setPayUPosId(String payUPosId) {
        this.payUPosId = payUPosId;
    }
}
