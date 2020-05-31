package com.csj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csj.domain.Country;
import com.csj.domain.Province;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 爬取疫情信息(丁香园)
 */
public class YiQingUtil {

    /**
     * 返回爬取的document对象
     * @return
     */
    public Document getDocument(){
        Document document = null;
        try {
            document = Jsoup.connect("https://ncov.dxy.cn/ncovh5/view/pneumonia").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 返回国内各省份疫情信息的map集合
     * @return
     */
    public Map<String, Province> getProvinces(){
        Document document = getDocument();
        //获取script标签内容-->json数据
        Elements scripts = document.getElementsByTag("script");
        String htmlDate = scripts.select("script[id=getAreaStat]").html();
        String jsonStr = htmlDate.substring(27,htmlDate.length()-11);
        JSONArray array = JSONArray.parseArray(jsonStr);
        //存入省实体类中
        Map<String,Province> provinceMap = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Province province = JSON.toJavaObject(jsonObject, Province.class);
            provinceMap.put(province.getProvinceShortName(),province);
        }
        return provinceMap;
    }

    /**
     * 返回各国家疫情信息的map集合
     * @return
     */
    public Map<String, Country> getCountries(){
        Document document = getDocument();
        Map<String,Country> countryMap = new HashMap<>();
        //获取script标签内容-->json数据
        Elements scripts = document.getElementsByTag("script");
        String htmlDate = scripts.select("script[id=getListByCountryTypeService2true]").html();
        String jsonStr = htmlDate.substring(48,htmlDate.length()-11);
        JSONArray array = JSON.parseArray(jsonStr);
        for (int i = 0; i <array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Country country = JSON.toJavaObject(jsonObject, Country.class);
            countryMap.put(country.getProvinceName(),country);
        }
        return countryMap;
    }



}
