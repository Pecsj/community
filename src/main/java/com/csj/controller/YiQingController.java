package com.csj.controller;

import com.alibaba.fastjson.JSON;
import com.csj.domain.Country;
import com.csj.domain.Province;
import com.csj.service.IYiQingService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 疫情信息控制器
 */
@Controller
public class YiQingController {
    @Autowired
    private IYiQingService service;

    @RequestMapping("/getFiveCountries")
    @ResponseBody
    public Object getFiveCountries(){
        //获取排序后的国家疫情信息
        List<Country> countryList = service.findSortCountries();

        List nameDate = new LinkedList();
        List valueDate = new LinkedList();
        Map date = new HashMap<>();
        Country country = null;
        for(int i=0;i<5;i++){
            country = countryList.get(i);
            if (country!=null){
                nameDate.add(country.getProvinceName());//存入国家名称
                valueDate.add(country.getConfirmedCount());//存入国家累计确诊数
            }
        }
        date.put("nameDate",nameDate);
        date.put("valueDate",valueDate);
        return date;
    }

    /**
     * 跳转到详细疫情信息界面
     * @return
     */
    @RequestMapping("/goInfoPage")
    public ModelAndView goInfoPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("infoPage");
        return mv;
    }

    /**
     * 获取中国疫情信息
     * @return
     */
    @RequestMapping("/getChinaInfo")
    @ResponseBody
    public List getChinaInfo(){
        List<Province> provinces = service.findProvinces();
        List<Map<String,Integer>> data = new LinkedList<>();
        for (Province province : provinces) {
            Map map = new HashMap();
            map.put("name",province.getProvinceShortName());
            map.put("value",province.getConfirmedCount());
            data.add(map);
        }
        return data;
    }

}
