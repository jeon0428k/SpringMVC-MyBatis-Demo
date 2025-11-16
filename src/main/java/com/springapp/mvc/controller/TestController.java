/*
* Test 가나다라마
* 
* */
package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    @RequestMapping(value = "/hello",
            method = RequestMethod.GET,
            produces="application/json;charset=UTF-8")
    public void hello(ModelMap model) {
        model.addAttribute("user", new User());
    }

}
