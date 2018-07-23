package com.larkersos.demo.web;

import com.larkersos.demo.service.IFeignService;
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

public class HiControler {
    @Autowired
    IFeignService iFeignService;
    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return iFeignService.sayHiFromClientOne(name);
    }
}
