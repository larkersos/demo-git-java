package com.larkersos.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>描述：</b>
 *
 * @author larker
 * @version Created by on 2018/7/11.
 */
@FeignClient(value = "service-hi",fallback = FeignHystrixServiceImpl.class)
public interface IFeignService {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
