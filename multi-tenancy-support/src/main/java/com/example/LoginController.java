package com.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @GetMapping("/login/oauth2")
    public RedirectView getAuthorizationCodeInitiationUrl(HttpServletRequest request) {
        return new RedirectView("/oauth2/authorization/tenant01");
    }
}
