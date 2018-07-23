package com.larkersos.demo.web;

import com.larkersos.demo.service.HelloHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>描述：</b>
 *
 * @author larker
 * @version Created by on 2018/7/11.
 */
@RestController
public class HelloHystrixControler {
    @Autowired
    HelloHystrixService helloHystrixService;
    @RequestMapping(value = "/helloHystrix")
    public String hi(@RequestParam String name){
        return helloHystrixService.hiService(name);
    }
}
