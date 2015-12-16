package com.oreumio.james.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jchoi on 12/14/15.
 */
@Controller
@RequestMapping("/mail")
public class MyController {
    @RequestMapping("first")
    public String page(HttpServletRequest request) {
        return "First";
    }

    @RequestMapping("name")
    @ResponseBody
    public MyVo name(HttpServletRequest request) {
        return new MyVo("First", "Last");
    }
}
