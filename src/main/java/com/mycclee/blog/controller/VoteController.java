package com.mycclee.blog.controller;

import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.service.VoteService;
import com.mycclee.blog.utils.ConstraintViolationExceptionUtils;
import com.mycclee.blog.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mycclee
 * @createTime 2019/7/25 14:24
 */
@Controller
@RequestMapping("votes")
public class VoteController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @PostMapping
    public ResponseEntity<ResponseUtils> createVote(Long blogId){
        User curUser = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        if (curUser == null){
            return ResponseEntity.ok(new ResponseUtils(false,"点赞请先登录"));
        }
        try {
            voteService.createVote(curUser,blogId);
            Blog blog = blogService.getBlogById(blogId);
            AtomicInteger i = new AtomicInteger(blog.getLikes());
            blog.setLikes(i.incrementAndGet());
            blogService.saveBlog(blog);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok(new ResponseUtils(false, ConstraintViolationExceptionUtils.getMessage(e)));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseUtils(false,e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true,"点赞成功",null));
    }

    @DeleteMapping
    public ResponseEntity<ResponseUtils> deleteVote(@RequestParam("blogId") Long blogId){
        User curUser = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        if (curUser == null) {
            return ResponseEntity.ok(new ResponseUtils(false,"点赞请先登录"));
        }
        try {
            voteService.removeVote(curUser,blogId);
            Blog blog = blogService.getBlogById(blogId);
            AtomicInteger i = new AtomicInteger(blog.getLikes());
            blog.setLikes(i.decrementAndGet());
            blogService.saveBlog(blog);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok(new ResponseUtils(false,ConstraintViolationExceptionUtils.getMessage(e)));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseUtils(false,e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true,"取消点赞成功",null));
    }
}
