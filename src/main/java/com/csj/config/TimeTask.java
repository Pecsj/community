package com.csj.config;

import com.csj.domain.Country;
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

    @Scheduled(cron = "0 30 * * * *")//测试使用值，每三十分钟更新一次
    public void updateYiQing(){
        System.out.println("定时任务**************更新疫情信息");
        YiQingUtil yiQingUtil = new YiQingUtil();
        Map<String, Country> countries = yiQingUtil.getCountries();
        service.updateCountries(countries);
    }


}
