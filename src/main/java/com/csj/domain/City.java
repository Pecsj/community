package com.csj.domain;

/**
 * 存储市级疫情信息实体类
 */
public class City {

    private String cityName;//城市名称
    private Integer currentConfirmedCount;//现存确诊
    private Integer confirmedCount;//累计确诊
    private Integer suspectedCount;//疑似
    private Integer curedCount;//治愈
    private Integer deadCount;//死亡

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(Integer currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public Integer getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(Integer confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public Integer getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(Integer suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public Integer getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(Integer curedCount) {
        this.curedCount = curedCount;
    }

    public Integer getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(Integer deadCount) {
        this.deadCount = deadCount;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", suspectedCount=" + suspectedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                '}';
    }
}
