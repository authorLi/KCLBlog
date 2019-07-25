package com.mycclee.blog.controller;

import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.domain.Comment;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.CommentService;
import com.mycclee.blog.service.EsBlogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.utils.ConstraintViolationExceptionUtils;
import com.mycclee.blog.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/14 21:53
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getCommentList(Long blogId, Model model) {
        User curUser = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> commentList = blog.getComments();
        model.addAttribute("comments", commentList);
        model.addAttribute("commentOwner", blog.getUser().getUsername());
        if (curUser == null){
            curUser = userService.getUserById(1L);
            return "userspace/loginFragment";
        }
        model.addAttribute("curUser",curUser.getUsername());
        return "userspace/blog :: #mainContainerReplace";
    }

    @PostMapping
    public ResponseEntity<ResponseUtils> addComment(Long blogId, String commentContent) {
        User curUser = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        if (curUser == null){
            return ResponseEntity.ok(new ResponseUtils(false,"发表评论请先登录"));
        }
        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok(new ResponseUtils(false, ConstraintViolationExceptionUtils.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseUtils(false, e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true, "添加评论成功", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtils> deleteComment(@PathVariable("id") Long id, Long blogId) {
        User ownUser = commentService.getCommentById(id).getUser();
        User curUser = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());

        if (!ownUser.getId().equals(curUser.getId())){
            return ResponseEntity.ok(new ResponseUtils(false,"你没有操作权限"));
        }
        try{
            blogService.removeComment(blogId,id);
            commentService.removeComment(id);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok(new ResponseUtils(false,ConstraintViolationExceptionUtils.getMessage(e)));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseUtils(false,e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true,"删除成功",null));
    }

}
