package com.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

// custom oauth2 login controller which can get tenant id from session
// then use it to redirect to the right oauth2 client authorization flow

@Controller
public class TenantAwareLoginController {

    @GetMapping("/login/oauth2")
    public RedirectView getAuthorizationCodeInitiationUrl(HttpServletRequest request) {
        // Get tenant Id from current session
        String tenantId = (String) request.getSession().getAttribute(Constants.TENANT_ID);
        System.out.println("Get tenantId from Session: " + tenantId);

        return new RedirectView("/oauth2/authorization/" + tenantId);
    }
}
