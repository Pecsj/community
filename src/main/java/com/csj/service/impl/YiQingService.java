package com.csj.service.impl;

import com.csj.domain.Country;
import com.csj.mapper.YiQingMapper;
import com.csj.service.IYiQingService;
import com.csj.util.YiQingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class YiQingService implements IYiQingService {
    @Autowired
    private YiQingMapper mapper;

    /**
     * 插入所有国家疫情信息（事务控制）
     * @return
     */
    @Override
    @Transactional
    public boolean insertCountries(Map<String, Country> countries) {
        boolean flag = false;
        if(countries==null||countries.size()==0){
            return false;
        }
        try {
            for (String key : countries.keySet()) {
                Country country = countries.get(key);
                mapper.insertCountry(country);
            }
            flag = true;
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新所有国家疫情信息
     * @param countries
     * @return
     */
    @Override
    public boolean updateCountries(Map<String, Country> countries) {
        boolean flag = false;
        if(countries==null||countries.size()==0){
            return false;
        }
        try {
            for (String key : countries.keySet()) {
                Country country = countries.get(key);
                mapper.updateCountry(country);
            }
            flag = true;
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Country findCountryByName(String name) {
        return mapper.findCountryByName("%"+name+"%");
    }

    @Override
    public List<Country> findSortCountries() {
        return mapper.findSortCountries();
    }


}
