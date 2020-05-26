package com.csj.mapper;

import com.csj.domain.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 疫情信息持久类
 */
@Repository
public interface YiQingMapper {

    boolean insertCountry(Country country);

    boolean updateCountry(Country country);

    Country findCountryByName(String name);

    List<Country> findSortCountries();
}