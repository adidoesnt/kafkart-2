package me.adityabanerjee.kafkart_backend.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "solace")
public class SolaceConfig {
    String host;
    String username;
    String password;
    String vpnName;
    String topic;
    int requestTimeout;

    public SolaceConfig(String host, String username, String password, String vpnName, String topic, int requestTimeout) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.vpnName = vpnName;
        this.topic = topic;
        this.requestTimeout = requestTimeout;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTopic() {
        return topic;
    }

    public String getVpnName() {
        return vpnName;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }
}
