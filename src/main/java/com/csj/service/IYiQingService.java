package com.csj.service;

import com.csj.domain.Country;

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
}
