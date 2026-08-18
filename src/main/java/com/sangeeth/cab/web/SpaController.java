package com.sangeeth.cab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({
            "/login",
            "/requests",
            "/manager",
            "/trips",
            "/employees"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
