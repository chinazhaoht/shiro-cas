package com.cyyz.shirocas.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/1/26.
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value="/home")
    @ResponseBody
    public String home(){
       return "hello";
    }
}
