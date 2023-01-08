package com.diakogiannis.psarros.propertyregistry.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to handle blocked users
 */
@Service
public class BlockedUserService {
    private static final Map<String,Long> userAttempts = new HashMap<>();

    @Value("${password.maxloginattempts}")
    Long maximumLoginAttempts;

    /**
     *
     * @param username
     */
    public void resetLogin(String username){
        if(!userAttempts.isEmpty() && userAttempts.get(username) != null){
            userAttempts.remove(username);
        }
    }

    /**
     *
     * @param username
     * @return
     */
    public Boolean isBlocked(String username){
        if(!userAttempts.isEmpty() && userAttempts.get(username) != null){
            if(userAttempts.get(username) >= maximumLoginAttempts){
                return true;
            } else {
                markFailure(username);
            }
        }
        return false;
    }

    /**
     *
     * @param username
     */
    public void markFailure(String username){
        if(userAttempts.isEmpty() || userAttempts.get(username) == null){
            userAttempts.put(username,1l);
        } else {
            userAttempts.put(username,userAttempts.get(username) + 1);
        }
    }

}
