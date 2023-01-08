package com.diakogiannis.psarros.propertyregistry.controller;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.PropertyType;
import com.diakogiannis.psarros.propertyregistry.service.defaults.ApplicationDefaultsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/play")
public class PlayController {

    @Autowired
    ApplicationDefaultsService applicationDefaultsService;

    @GetMapping(path = "/teststuff")
    public ResponseEntity getTestStuff() {
        applicationDefaultsService.getDefaultSetValues();
        return new ResponseEntity(HttpStatus.OK);
    }

}
