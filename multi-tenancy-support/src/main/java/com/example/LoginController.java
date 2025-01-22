package com.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/login/oauth2")
    public RedirectView getAuthorizationCodeInitiationUrl(HttpServletRequest request) {
        // Access the HttpServletRequest
        String requestUri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String parameters = Utils.mapToString(parameterMap);
        String method = request.getMethod();
        System.out.println("Request URI: " + requestUri + ", Parameters: " + parameters + ", Method: " + method);

        String tenantId = (String) request.getSession().getAttribute(Constants.TENANT_ID);
        System.out.println("Get tenantId from Session: " + tenantId);

        return new RedirectView("/oauth2/authorization/" + tenantId);
    }
}
