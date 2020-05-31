package com.csj.service;

import com.csj.domain.Country;
import com.csj.domain.Province;

import java.util.List;
import java.util.Map;

/**
 * 疫情信息业务层
 */
public interface IYiQingService {

    boolean insertCountries(Map<String, Country> countries);

    boolean updateCountries(Map<String, Country> countries);

    Country findCountryByName(String name);

    List<Country> findSortCountries();

    boolean insertProvinces(Map<String, Province> provinces);

    boolean updateProvinces(Map<String, Province> provinces);

    List<Province> findProvinces();
}
