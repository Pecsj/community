package com.csj.controller;

import com.csj.domain.Country;
import com.csj.domain.Province;
import com.csj.service.IYiQingService;
import com.csj.util.YiQingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    private IYiQingService service;

    @RequestMapping("/test")
    @ResponseBody
    public String test1() throws Exception{
        YiQingUtil util = new YiQingUtil();
        Map<String, Province> provinces = util.getProvinces();
        service.updateProvinces(provinces);
        return "1";
    }

}
