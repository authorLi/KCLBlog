package com.mycclee.blog.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author mycclee
 * @createTime 2019/7/23 20:20
 */
@Document(indexName = "blog",type = "blog")
@XmlRootElement
public class EsBlog implements Serializable {

    @Id
    private String id;

    @Field(index = false)
    private Long blogId;

    private String title;

    private String summary;

    private String content;

    @Field(index = false,fielddata = true)
    private String username;

    @Field(index = false)
    private String avatar;

    @Field(index = false)
    private Timestamp createTime;

    @Field(index = false)
    private Integer viewed;

    @Field(index = false)
    private Integer comment;

    @Field(index = false)
    private Integer likes;

    private String tags;

    public EsBlog(){}

    public EsBlog(String title,String content){
        this.title = title;
        this.content = content;
    }

    public EsBlog(Long blogId, String title, String summary, String content, String username, String avatar, Timestamp createTime, Integer viewed, Integer comment, Integer likes, String tags) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.viewed = viewed;
        this.comment = comment;
        this.likes = likes;
        this.tags = tags;
    }

    public EsBlog(Blog blog){
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.avatar = blog.getUser().getAvatar();
        this.username = blog.getUser().getUsername();
        this.comment = blog.getComment();
        this.createTime = blog.getCreateTime();
        this.likes = blog.getLikes();
        this.summary = blog.getSummary();
        this.viewed = blog.getViewed();
        this.tags = blog.getTags();
    }

    public void update(Blog blog){
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.avatar = blog.getUser().getAvatar();
        this.username = blog.getUser().getUsername();
        this.comment = blog.getComment();
        this.createTime = blog.getCreateTime();
        this.likes = blog.getLikes();
        this.summary = blog.getSummary();
        this.viewed = blog.getViewed();
        this.tags = blog.getTags();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", viewed=" + viewed +
                ", comment=" + comment +
                ", likes=" + likes +
                ", tags='" + tags + '\'' +
                '}';
    }
}
