package com.danzal.lib_project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {

        //model.addAttribute("authors", authorService.getAuthors());
        return "index";
    }
}
