package com.mycclee.blog.controller;

import com.mycclee.blog.domain.User;
import com.mycclee.blog.realm.AuthRealm;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.utils.ResponseUtils;
import com.mycclee.blog.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * @author mycclee
 * @createTime 2019/7/10 21:31
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public ModelAndView createForm(Model model){
        model.addAttribute("userModel",new User(null,null,null,null));
        return new ModelAndView("userspace/add","model",model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView update(@PathVariable("id") Long id,Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("title","修改用户");
        return new ModelAndView("user/edit","model",model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtils> delete(@PathVariable("id") Long id,Model model){
        try{
            userService.removeUser(id);
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseUtils(false,e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true,"处理成功"));
    }
}
