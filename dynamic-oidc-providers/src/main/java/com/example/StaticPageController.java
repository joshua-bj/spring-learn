package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

    @GetMapping("/restricted")
    public String serveLandPage() {
        // Maps "/restricted" to "restricted/landing.html" in the static folder
        return "restricted/landing.html"; //somehow, the html extension name MUST be included
    }
}
