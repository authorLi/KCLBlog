package com.mycclee.blog.service.Impl;

import com.mycclee.blog.domain.*;
import com.mycclee.blog.repository.BlogRepository;
import com.mycclee.blog.repository.UserRepository;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.CommentService;
import com.mycclee.blog.service.EsBlogService;
import org.apache.shiro.SecurityUtils;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/13 10:28
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EsBlogService esBlogService;

    @Override
    public Page<Blog> getAllBlogs(Integer pageNum, Integer pageSize) {

        PageRequest pageAble = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC,"createTime");

        Page<Blog> blogs = blogRepository.findAll(pageAble);

        return blogs;
    }

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        boolean isNew = (blog.getId() == null);
        EsBlog esBlog = null;
        Blog returnBlog = blogRepository.save(blog);
        if (isNew){
            esBlog = new EsBlog(returnBlog);
        }else {
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(returnBlog);
        }
        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBlogById(Long id) {
        blogRepository.deleteById(id);
        esBlogService.removeEsBlog(esBlogService.getEsBlogByBlogId(id).getId());
    }

    @Override
    public Page<Blog> getAllBlogByUser(Integer pageNum, Integer pageSize, User user) {
        PageRequest pageable = PageRequest.of(pageNum,pageSize);
        return blogRepository.getAllByUser(user,pageable);
    }

    @Override
    public Blog createComment(Long blogId,String commentContent) {
        Blog originBlog = blogRepository.getOne(blogId);
        User user = userRepository.findByUsername((String) SecurityUtils.getSubject().getPrincipal());
        originBlog.addComment(new Comment(commentContent,user,new Date()));
        return saveBlog(originBlog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog originBlog = blogRepository.getOne(blogId);
        originBlog.removeComment(commentId);
        saveBlog(originBlog);
    }

    @Override
    public Page<Blog> getAllByCatalog(Integer pageNum,Integer pageSize,Catalog catalog) {
        PageRequest pageable = PageRequest.of(pageNum,pageSize);
        return blogRepository.getAllByCatalogOrderByCreateTimeDesc(catalog,pageable);
    }


}
