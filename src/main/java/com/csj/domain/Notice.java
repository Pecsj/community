package com.csj.domain;

/**
 * 通知公告实体类
 */
public class Notice {
    private Integer nid;
    private Integer uid;
    private String contain;
    private Long createTime;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "nid=" + nid +
                ", uid=" + uid +
                ", contain='" + contain + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
