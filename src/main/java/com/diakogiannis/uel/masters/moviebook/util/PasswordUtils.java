package com.diakogiannis.uel.masters.moviebook.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Slf4j
@Component
public class PasswordUtils implements
        ApplicationListener<ContextRefreshedEvent> {

    @Value("${password.filepath}")
    String passwordFilepath;

    private static final Set<String> PASSWORDS = new HashSet<>();

    public Set<String> fetchCommonPassword() throws IOException {
        File resource = new ClassPathResource(passwordFilepath).getFile();
        Set<String> passwords = new HashSet<>();
        Scanner textFile = new Scanner(resource);
        while (textFile.hasNext()){
            passwords.add(textFile.next().trim());
        }
        return passwords;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Loading common passwords, this might take some time...");
        try {
            PASSWORDS.addAll(fetchCommonPassword());
            log.info("Passwords loaded!");
        } catch (IOException e) {
            log.error("Loading of passwords failed with message {}",e.getMessage(),e);
        }
    }

    public Set<String> getPasswords(){
        return PASSWORDS;
    }


}
