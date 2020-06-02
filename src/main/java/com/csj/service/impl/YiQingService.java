package com.csj.service.impl;

import com.csj.domain.Country;
import com.csj.domain.Province;
import com.csj.mapper.YiQingMapper;
import com.csj.service.IYiQingService;
import com.csj.util.YiQingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    /**
     * 插入各省份疫情信息
     * @param provinces
     * @return
     */
    @Override
    @Transactional
    public boolean insertProvinces(Map<String, Province> provinces) {
        boolean flag = false;
        if(provinces.size()>0){
            try {
                Iterator<String> iterator = provinces.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    Province province = provinces.get(key);
                    mapper.insertProvince(province);
                }
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 更新各省份疫情信息
     * @param provinces
     * @return
     */
    @Override
    public boolean updateProvinces(Map<String, Province> provinces) {
        boolean flag = false;
        if(provinces.size()>0){
            try {
                Iterator<String> iterator = provinces.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    Province province = provinces.get(key);
                    mapper.updateProvince(province);
                }
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 获取各省份疫情信息
     * @return
     */
    @Override
    public List<Province> findProvinces() {
        return mapper.findProvinces();
    }

    /**
     * 获取各国家疫情信息
     * @return
     */
    @Override
    public List<Country> findCountries() {
        return mapper.findCountries();
    }


}
