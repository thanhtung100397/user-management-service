package com.service.usermanagement.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;

@Component
public class HostContributor implements InfoContributor {
    @Value("${server.port}")
    private int port;

    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, Object> info = new HashMap<>();
        info.put("address", InetAddress.getLoopbackAddress().getHostAddress());
        info.put("port", port);
        builder.withDetail("host", info);
    }
}
