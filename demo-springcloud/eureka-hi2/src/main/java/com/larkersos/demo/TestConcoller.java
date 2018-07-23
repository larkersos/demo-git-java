package com.larkersos.demo;

import org.springframework.beans.factory.annotation.Value;
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
public class TestConcoller {

    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi2 "+name+",i am from port:" +port;
    }
}
