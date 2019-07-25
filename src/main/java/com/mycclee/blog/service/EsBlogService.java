package com.mycclee.blog.service;

import com.mycclee.blog.domain.EsBlog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.utils.TagUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/23 20:42
 */
public interface EsBlogService {
    /**
     * 删除Blog
     * @param id
     * @return
     */
    void removeEsBlog(String id);

    /**
     * 更新 EsBlog
     * @param esBlog
     * @return
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据id获取Blog
     * @param blogId
     * @return
     */
    EsBlog getEsBlogByBlogId(Long blogId);

    /**
     * 根据username获取EsBlog
     * @param username
     * @param pageable
     * @return
     */
    Page<EsBlog> getAllEsBlogByUsername(String username,Pageable pageable);

    /**
     * 最新博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    /**
     * 最热博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    /**
     * 最新博客列表根据用户名查询，分页
     * @param username
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogsByUsername(String username,String keyword,Pageable pageable);

    /**
     * 最热博客列表根据用户名查询，分页
     * @param username
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHotestEsBlogsByUsername(String username,String keyword,Pageable pageable);

    /**
     * 博客列表，分页
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);
    /**
     * 最新前5
     * @param
     * @return
     */
    List<EsBlog> listTop5NewestEsBlogs();

    /**
     * 最热前5
     * @param
     * @return
     */
    List<EsBlog> listTop5HotestEsBlogs();

    /**
     * 最热前30个标签
     * @return
     */
    List<TagUtils> listTop30Tags();

    /**
     * 最热前12用户
     * @return
     */
    List<User> listTop12Users();
}
