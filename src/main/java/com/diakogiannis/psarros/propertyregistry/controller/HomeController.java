package com.diakogiannis.psarros.propertyregistry.controller;

import com.diakogiannis.psarros.propertyregistry.enums.UrlBindingsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getIndex() {
        log.debug("Entering Index...");
        return "redirect:" + UrlBindingsEnum.MOVIES_HOME_URI.getValue();
    }

}
