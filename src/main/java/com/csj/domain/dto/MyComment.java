package com.csj.domain.dto;

import java.util.List;
import java.util.Map;

/**
 * 我的回复
 */
public class MyComment {

    private int aid;//文章id
    private String title;//文章标题
    private String description;//文章描述
    private String uName;//作者名称
    private String avatarUrl;//作者头像
    private List<ArticleComment> commentList;//回复列表

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<ArticleComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ArticleComment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "MyComment{" +
                "aid=" + aid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", uName='" + uName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
