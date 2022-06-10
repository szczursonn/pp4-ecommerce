package pl.mszcz.config;

import org.springframework.boot.context.properties.*;

// https://www.baeldung.com/configuration-properties-in-spring-boot#1-spring-boot-22
@ConfigurationProperties(prefix = "ecommerce")
public class AppProperties {

    private String clientId;
    private String clientSecret;
    private String posId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
}
