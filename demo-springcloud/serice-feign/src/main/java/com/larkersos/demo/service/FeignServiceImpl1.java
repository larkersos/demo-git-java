package com.larkersos.demo.service;

import com.larkersos.demo.service.IFeignService;
import org.springframework.stereotype.Component;

/**
 * <b>描述：</b>
 *
 * @author larker
 * @version Created by on 2018/7/11.
 */
@Component
public class FeignServiceImpl1 implements IFeignService {
    @Override
    public String sayHiFromClientOne(String name){
        return "Sorry,"+name;
    }
}
