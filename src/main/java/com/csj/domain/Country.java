package com.csj.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存储国家疫情信息实体类
 */
public class Country {
    private Long modifyTime;//更新时间
    private String continents;//洲
    private String provinceName;//国家名称
    private Integer currentConfirmedCount;//现存确诊
    private Integer confirmedCount;//累计确诊
    private Integer confirmedCountRank;//确诊数排名
    private Integer curedCount;//治愈数
    private Integer deadCount;//死亡数
    private Integer deadCountRank;//死亡数排名
    private Double deadRate;//死亡率
    private Integer deadRateRank;//死亡率排名

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        try{
            if(modifyTime.length()>10){//当是疫情信息写入时长度大于10
                modifyTime = modifyTime.substring(0,10);
                this.modifyTime = Long.parseLong(modifyTime);
            }else {//当时数据库写入时长度等于10
                SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(modifyTime);
                this.modifyTime = date.getTime();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getContinents() {
        return continents;
    }

    public void setContinents(String continents) {
        this.continents = continents;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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

    public Integer getConfirmedCountRank() {
        return confirmedCountRank;
    }

    public void setConfirmedCountRank(Integer confirmedCountRank) {
        this.confirmedCountRank = confirmedCountRank;
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

    public Integer getDeadCountRank() {
        return deadCountRank;
    }

    public void setDeadCountRank(Integer deadCountRank) {
        this.deadCountRank = deadCountRank;
    }

    public Double getDeadRate() {
        return deadRate;
    }

    public void setDeadRate(Double deadRate) {
        this.deadRate = deadRate;
    }

    public Integer getDeadRateRank() {
        return deadRateRank;
    }

    public void setDeadRateRank(Integer deadRateRank) {
        this.deadRateRank = deadRateRank;
    }

    @Override
    public String toString() {
        return "Country{" +
                "modifyTime=" + modifyTime +
                ", continents='" + continents + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", confirmedCountRank=" + confirmedCountRank +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", deadCountRank=" + deadCountRank +
                ", deadRate=" + deadRate +
                ", deadRateRank=" + deadRateRank +
                '}';
    }
}
