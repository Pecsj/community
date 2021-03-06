package com.csj.domain;

import java.io.Serializable;

public class Article implements Serializable {
    private Integer id;
    private String title;
    private String description;//内容
    private String tag;//标签
    private Long gmtCreat;//发表时间
    private Long gmtModified;//最后修改时间
    private Integer creator;//作者id
    private Integer viewCount;//查看数
    private Integer commentCount;//评论数
    private Integer likeCount;//喜欢数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public Long getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(Long gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", gmtCreat=" + gmtCreat +
                ", gmtModified=" + gmtModified +
                ", creator=" + creator +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                '}';
    }
}
