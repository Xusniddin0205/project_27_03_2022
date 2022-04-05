package com.demo.demo.security.audit;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionDestroyListener implements ApplicationListener<HttpSessionDestroyedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(SessionDestroyListener.class);
    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent httpEvent) {
        httpEvent.getSecurityContexts().stream().forEach(securityContext -> {
           log.error("Session destroyed"+securityContext.getAuthentication().getPrincipal().toString());
        });
    }
}
