package com.diakogiannis.uel.masters.moviebook.controller;

import com.diakogiannis.uel.masters.moviebook.enums.UrlBindingsEnum;
import com.diakogiannis.uel.masters.moviebook.exceptions.UserExistsException;
import com.diakogiannis.uel.masters.moviebook.model.dto.UsersDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import com.diakogiannis.uel.masters.moviebook.model.mappers.UsersMapper;
import com.diakogiannis.uel.masters.moviebook.model.misc.ModalInfo;
import com.diakogiannis.uel.masters.moviebook.service.UserService;
import com.diakogiannis.uel.masters.moviebook.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UsersMapper usersMapper;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @Autowired
    PasswordUtils passwordUtils;

    @GetMapping(path = "/principal")
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }

    /**
     * Sanitized username to cope with Thymneleaf vulnerability CWE-79  CVSS 6.5
     * @param model
     * @param usersDTO
     * @param result
     * @return
     *
     */
    @PostMapping(path = "/register")
    public String registerUser(Model model, @Valid UsersDTO usersDTO, BindingResult result) {
        if (result.hasErrors()) {
            return UrlBindingsEnum.USER_REGISTER_TEMPLATE.getValue();
        }
        try {
            Users user = usersMapper.toUsers(usersDTO);
            //check is password exists in common password list
            if(passwordUtils.getPasswords().contains(user.getPassword())){
                model.addAttribute("modalInfo", new ModalInfo("Error!", "Password exists in list of common passwords!"));
            } else {
                //Sanitized username to cope with Thymneleaf vulnerability CWE-79  CVSS 6.5
                user.setUsername(Jsoup.clean(user.getUsername().toString(), Safelist.basic()));
                userService.registerUser(user);
                model.addAttribute("created", true);
            }
        } catch (UserExistsException e) {
            model.addAttribute("modalInfo", new ModalInfo("Error!", "User Already Exists!"));
        }

        return UrlBindingsEnum.USER_REGISTER_TEMPLATE.getValue();
    }

    @GetMapping(path = "/register")
    public String registerUserPage(Model model) {
        model.addAttribute("usersDTO", new UsersDTO());
        return UrlBindingsEnum.USER_REGISTER_TEMPLATE.getValue();
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/process-login")
    public String processLogin(Principal principal) {
        session.setAttribute("userDetailsDTO", userService.getUserDetails(principal.getName()));
        return "redirect:/" + UrlBindingsEnum.MOVIES_HOME_URI.getValue();
    }

}
