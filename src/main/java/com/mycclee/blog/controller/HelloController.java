package com.mycclee.blog.controller;

import com.mycclee.blog.domain.User;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mycclee
 * @createTime 2019/7/8 22:04
 */
@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    @ResponseBody
    public String helloWorld() {
        return "你好，祝你成功！";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "redirect:/blogs";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        }catch (Exception e){
            model.addAttribute("loginError",true);
            model.addAttribute("errorMessage",e.getMessage());
            return "login";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String registHtml() {
        return "register";
    }

    @PostMapping("/register")
    public String regist(User user) {
        user.setPassword(ShiroUtils.md5(user.getPassword(),user.getUsername()));
        userService.saveUser(user);
        return "redirect:login";
    }

}
