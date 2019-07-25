package com.mycclee.blog.controller;

import com.alibaba.druid.sql.PagerUtils;
import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.domain.EsBlog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.service.BlogService;
import com.mycclee.blog.service.EsBlogService;
import com.mycclee.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/13 10:25
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    private static final Integer pageSize = 5;

    @Autowired
    private EsBlogService esBlogService;

    @GetMapping
    public ModelAndView blogList(@RequestParam(value = "pageNum",defaultValue = "-2",required = false) Integer pageNum, Model model){
        boolean flag = false;
        if (pageNum == -2){
            flag = true;
            pageNum = 0;
        }
        Page<EsBlog> blogList = esBlogService.listEsBlogs(PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
        model.addAttribute("blogModel",blogList);
        rightModel(model);
        if (flag) {
            return new ModelAndView("index", "model", model);
        }
        return new ModelAndView("userspace/indexBlogList.html","model",model);
    }

    @GetMapping("/search")
    public ModelAndView getOneTag(@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum,
                                  @RequestParam(value = "keyword",required = false) String keyword,
                                  @RequestParam(value = "order",defaultValue = "hot",required = false) String order,
                                  Model model) {
        Page<EsBlog> blogs = null;
        if ("none".equals(keyword)){
            try {
                if (order.equals("hot")) { // 最热查询
                    blogs = esBlogService.listEsBlogs(PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"comment","viewed")));
                } else if (order.equals("new")) { // 最新查询
                    blogs = esBlogService.listEsBlogs(PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
                }
            } catch (Exception e) {
                blogs = esBlogService.listEsBlogs(PageRequest.of(pageNum,pageSize));
            }
        }else {
            blogs = esBlogService.listHotestEsBlogs(keyword, PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"comment","viewed")));
        }
        model.addAttribute("blogModel",blogs);
        rightModel(model);
        return new ModelAndView("index.html","model",model);
    }


    private Model rightModel(Model model){
        model.addAttribute("tags",esBlogService.listTop30Tags());
        model.addAttribute("newest",esBlogService.listTop5NewestEsBlogs());
        model.addAttribute("hotest",esBlogService.listTop5HotestEsBlogs());
        model.addAttribute("users",esBlogService.listTop12Users());
        return model;
    }
}
