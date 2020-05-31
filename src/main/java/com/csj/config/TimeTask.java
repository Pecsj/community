package com.csj.config;

import com.csj.domain.Country;
import com.csj.domain.Province;
import com.csj.service.IYiQingService;
import com.csj.util.YiQingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

/**
 * 定时更新疫情信息
 */
@Configuration
@EnableScheduling
public class TimeTask {
    @Autowired
    private IYiQingService service;

    @Scheduled(cron = "0 0 0/3 * * *")//三小时更新一次（测试：1分钟）
    public void updateYiQing(){
        System.err.println("定时任务*****************【更新疫情信息】");
        try{
            //更新个国家疫情信息
            YiQingUtil yiQingUtil = new YiQingUtil();
            Map<String, Country> countries = yiQingUtil.getCountries();
            service.updateCountries(countries);
            //更新国内各省份疫情信息
            Map<String, Province> provinces = yiQingUtil.getProvinces();
            service.updateProvinces(provinces);

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("定时更新疫情信息失败");
        }
    }


}
