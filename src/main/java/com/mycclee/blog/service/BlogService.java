package com.mycclee.blog.service;

import com.mycclee.blog.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author mycclee
 * @createTime 2019/7/13 10:28
 */
public interface BlogService {

    Page<Blog> getAllBlogs(Integer pageNum, Integer pageSize);

    Blog saveBlog(Blog blog);

    Blog getBlogById(Long id);

    void removeBlogById(Long id);

    Page<Blog> getAllBlogByUser(Integer pageNum, Integer pageSize, User user);

    Blog createComment(Long id, String commentContent);

    void removeComment(Long blogId, Long commentId);

    Page<Blog> getAllByCatalog(Integer pageNum, Integer pageSize, Catalog catalog);
}
