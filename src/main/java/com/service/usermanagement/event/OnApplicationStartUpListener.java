package com.service.usermanagement.event;

import com.service.usermanagement.services.ConsulRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class OnApplicationStartUpListener implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(OnApplicationStartUpListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Service address: http://" + InetAddress.getLoopbackAddress().getHostAddress());
        ConsulRegistrationService consulRegistrationService = event.getApplicationContext()
                .getBean(ConsulRegistrationService.class);
        ResponseEntity response = consulRegistrationService.registerServiceToConsul();
        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Register service to consul server successfully");
        } else {
            logger.error("Unable to register service to consul server", response);
        }
    }
}
