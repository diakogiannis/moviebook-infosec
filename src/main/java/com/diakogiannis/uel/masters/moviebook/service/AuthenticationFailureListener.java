package com.diakogiannis.uel.masters.moviebook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    BlockedUserService blockedUserService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        blockedUserService.markFailure(event.getAuthentication().getName());
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            log.error("Failed login attempt for {} from {}", event.getAuthentication().getName(), request.getRemoteAddr());
        } else {
            log.error("Failed login attempt for {} from {}", event.getAuthentication().getName(), xfHeader.split(",")[0]);
        }
    }


}
