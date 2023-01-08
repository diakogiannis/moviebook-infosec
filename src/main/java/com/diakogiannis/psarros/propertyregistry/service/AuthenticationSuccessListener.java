package com.diakogiannis.psarros.propertyregistry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    BlockedUserService blockedUserService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if(blockedUserService.isBlocked(event.getAuthentication().getName())){
            log.error("User {} Blocked for BFA!",event.getAuthentication().getName());
            request.getSession().invalidate();
            return;
        }
        blockedUserService.resetLogin(event.getAuthentication().getName());
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            log.error("Successful login attempt for {} from {}", event.getAuthentication().getName(), request.getRemoteAddr());
        } else {
            log.error("Successful login attempt for {} from {}", event.getAuthentication().getName(), xfHeader.split(",")[0]);
        }
    }
}
