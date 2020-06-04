package com.csj.domain.dto;

/**
 * 文章评论
 */
public class ArticleComment {
    private int cid;//评论id
    private int aid;//文章id
    private int uid;//评论人id
    private String name;//评论人姓名
    private String avatarUrl;//评论人头像
    private String contain;//评论内容
    private int likeCount;//喜欢数
    private String commentTime;//评论时间
    private boolean isLike;//当前用户是否喜欢

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean like) {
        isLike = like;
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
                "cid=" + cid +
                ", aid=" + aid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", contain='" + contain + '\'' +
                ", likeCount=" + likeCount +
                ", commentTime='" + commentTime + '\'' +
                ", isLike=" + isLike +
                '}';
    }
}
