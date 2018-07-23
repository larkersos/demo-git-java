package com.larkersos.demo.service;

import org.springframework.stereotype.Component;

/**
 * <b>描述：</b>
 *
 * @author larker
 * @version Created by on 2018/7/11.
 */
@Component
public class FeignHystrixServiceImpl implements IFeignService{
    @Override
    public String sayHiFromClientOne(String name) {
        return "FeignHystrix : sorry "+name;
    }
}
