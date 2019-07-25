package com.mycclee.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/9 9:38
 */
@Entity
@Table(name = "KCL_BLOGS")
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2,max = 50)
    @Column(nullable = false,length = 50)
    private String title;

    @NotEmpty(message = "摘要不能为空")
    @Size(min = 10,max = 200)
    @Column(nullable = false)
    private String summary;

    private Timestamp createTime;

    @Lob
    @Size(min = 2)
    @Column(nullable = false)
    @NotEmpty(message = "内容不能为空")
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Lob
    @Size(min = 2)
    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    private String htmlContent;

    @Column(name = "viewed")
    private Integer viewed = 0;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "comment")
    private Integer comment = 0;

    @OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment",joinColumns = @JoinColumn(name = "blog_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "comment_id",referencedColumnName = "id"))
    private List<Comment> comments;


    @OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Catalog catalog;

    @Column(name = "tags",length = 100)
    private String tags;

    protected Blog(){}

    public Blog(String title,String summary,String content){
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
        this.comment = this.comments.size();
    }

    public void removeComment(Long commentId){
        for (int i = 0;i < comments.size();i++){
            if (comments.get(i).getId().equals(commentId)){
                this.comments.remove(i);
                break;
            }
        }
        this.comment = this.comments.size();
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", viewed=" + viewed +
                ", likes=" + likes +
                ", comment=" + comment +
                ", user=" + user +
                ", comments=" + comments +
                ", catalog=" + catalog +
                ", tags='" + tags + '\'' +
                '}';
    }
}
