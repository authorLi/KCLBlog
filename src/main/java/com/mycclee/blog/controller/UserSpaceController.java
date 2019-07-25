package com.mycclee.blog.controller;

import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.domain.Catalog;
import com.mycclee.blog.domain.EsBlog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.service.*;
import com.mycclee.blog.utils.ConstraintViolationExceptionUtils;
import com.mycclee.blog.utils.ResponseUtils;
import com.mycclee.blog.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author mycclee
 * @createTime 2019/7/13 18:14
 */
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    private static final Integer pageSize = 5;

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private EsBlogService esBlogService;
    @Autowired
    private VoteService voteService;

    @GetMapping
    public String toUserSpace() {
        Subject subject = SecurityUtils.getSubject();
        return "redirect:/u/" + subject.getPrincipal();
    }

    @GetMapping("/{username}")
    public ModelAndView userSpace(@PathVariable("username") String username,
                                  @RequestParam(value = "pageNum", defaultValue = "-2", required = false) Integer pageNum, Model model) {

        boolean flag = false;
        if (pageNum == -2){
            flag = true;
            pageNum = 0;
        }
        User user = userService.getUserByUserName(username);
        Page<Blog> blogs = blogService.getAllBlogByUser(pageNum, pageSize, user);
        model.addAttribute("userModel", user);
        model.addAttribute("blogModel", blogs);
        if (flag){
            return new ModelAndView("userspace/u", "model", model);
        }
        return new ModelAndView("userspace/blogList","model",model);
    }

    @GetMapping("/profile")
    public ModelAndView profile(Model model) {
        model.addAttribute("userModel", userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        return new ModelAndView("userspace/profile", "model", model);
    }

    @PostMapping("/{username}/profile")
    public String modifyProfile(@PathVariable("username") String username, User user) {
        User originUser = userService.getUserByUserName(username);
        System.out.println(user);
        System.out.println(originUser);
        originUser.setEmail(user.getEmail());
        originUser.setPassword(ShiroUtils.md5(user.getPassword(), username));
        originUser.setName(user.getName());
        userService.updateUser(originUser);
        return "redirect:/u/profile";
    }

    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlogView(@PathVariable("username") String username, Model model) {
        User user = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        Blog blog = new Blog(null, null, null);
        List<Catalog> catalogList = catalogService.getCatalogList(user);
        model.addAttribute("catalogs", catalogList);
        model.addAttribute("userModel", user);
        model.addAttribute("blogModel", blog);
        return new ModelAndView("userspace/blogEdit", "model", model);
    }

    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlogView(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getBlogById(id);
        User user = userService.getUserByUserName(username);
        List<Catalog> catalogList = catalogService.getCatalogList(user);
        model.addAttribute("catalogs", catalogList);
        model.addAttribute("userModel", user);
        model.addAttribute("blogModel", blog);
        return new ModelAndView("userspace/blogEdit", "model", model);
    }

    @PostMapping("/{username}/blogs/edit")
    public ResponseEntity<ResponseUtils> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        String url = "";
        Blog returnBlog = null;
        if (blog.getCatalog().getId() == null) {
            return ResponseEntity.ok(new ResponseUtils(false, "未选择分类"));
        }
        if (null == blog.getId()) {
            User user = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
            blog.setUser(user);
            blog.setCreateTime(new Timestamp(System.currentTimeMillis()));
            try {
                returnBlog = blogService.saveBlog(blog);
            } catch (ConstraintViolationException e) {
                return ResponseEntity.ok(new ResponseUtils(false, ConstraintViolationExceptionUtils.getMessage(e)));
            } catch (Exception e) {
                return ResponseEntity.ok(new ResponseUtils(false, e.getMessage()));
            }
            url = "/u/view/" + user.getUsername() + "/blogs/" + returnBlog.getId();
            return ResponseEntity.ok(new ResponseUtils(true, "添加成功", url));
        } else {
            Blog originBlog = blogService.getBlogById(blog.getId());
            originBlog.setTitle(blog.getTitle());
            originBlog.setSummary(blog.getSummary());
            originBlog.setContent(blog.getContent());
            originBlog.setCatalog(blog.getCatalog());
            originBlog.setTags(blog.getTags());
            try {
                blogService.saveBlog(originBlog);
            } catch (ConstraintViolationException e) {
                return ResponseEntity.ok(new ResponseUtils(false, ConstraintViolationExceptionUtils.getMessage(e)));
            } catch (Exception e) {
                return ResponseEntity.ok(new ResponseUtils(false, e.getMessage()));
            }
            url = "/u/view/" + originBlog.getUser().getUsername() + "/blogs/" + originBlog.getId();
            return ResponseEntity.ok(new ResponseUtils(true, "修改成功", url));
        }
    }

    @DeleteMapping("/{username}/blogs/{id}")
    public ResponseEntity<ResponseUtils> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        try {
            blogService.removeBlogById(id);
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseUtils(false, e.getMessage()));
        }
        String url = "/u/" + username + "/blogs";
        return ResponseEntity.ok(new ResponseUtils(true, "删除成功", url));
    }

    @GetMapping("/view/{username}/blogs/{id}")
    public String showBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        boolean flag = true;
        User curUser = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        if (curUser == null){
            curUser = userService.getUserById(1L);
            flag = false;
        }
        Blog blog = blogService.getBlogById(id);
        if (flag && !voteService.viewedHasUser(curUser,id)){
            voteService.addViewed(curUser,id);
            AtomicInteger i = new AtomicInteger(blog.getViewed());
            blog.setViewed(i.incrementAndGet());
            blog = blogService.saveBlog(blog);
        }
        List<EsBlog> blogList = esBlogService.getAllEsBlogByUsername(username, PageRequest.of(0,5,new Sort(Sort.Direction.DESC,"comment","viewed"))).getContent();
        model.addAttribute("userModel", blog.getUser());
        model.addAttribute("blogModel", blog);
        model.addAttribute("isBlogOwner", username.equals(curUser.getUsername()) ? true : false);
        model.addAttribute("currentVote", voteService.hasUser(curUser,id));
        model.addAttribute("blogListModel", blogList);
        return "userspace/blog";
    }

    @GetMapping("/{username}/avatar")
    public ModelAndView getAvatarView(@PathVariable("username") String username, Model model) {
        User user = userService.getUserByUserName((String) SecurityUtils.getSubject().getPrincipal());
        model.addAttribute("userModel", user);
        return new ModelAndView("userspace/avatar", "model", model);
    }

    @PostMapping("/{username}/avatar")
    public ResponseEntity<ResponseUtils> saveAvatar(@PathVariable("username") String username,@RequestBody User user){
        User originUser = userService.getUserById(user.getId());
        originUser.setAvatar(user.getAvatar());
        userService.saveUser(originUser);
        return ResponseEntity.ok(new ResponseUtils(true,"修改成功",user.getAvatar()));
    }

    @GetMapping("/{username}/blogs/catalog/{id}")
    public ModelAndView getOneCatalog(@PathVariable("username") String username, @PathVariable("id") Long id,
                                      @RequestParam(value = "pageNum", defaultValue = "-2", required = false) Integer pageNum, Model model) {
        boolean flag = false;
        if (pageNum == -2){
            flag = true;
            pageNum = 0;
        }
        User user = userService.getUserByUserName(username);
        Catalog catalog = catalogService.getCatalogById(id);
        Page<Blog> blogs = blogService.getAllByCatalog(pageNum, pageSize, catalog);
        model.addAttribute("userModel",user);
        model.addAttribute("blogModel",blogs);
        if (flag) {
            return new ModelAndView("userspace/u", "model", model);
        }
        return new ModelAndView("userspace/blogList","model",model);
    }

    @GetMapping("/{username}/blogs/tag")
    public ModelAndView getOneTag(@PathVariable("username") String username,
                                  @RequestParam(value = "pageNum", defaultValue = "-2", required = false) Integer pageNum,
                                  @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                                  @RequestParam(value = "order",defaultValue = "new",required = false) String order,
                                  @RequestParam(value = "async",required = false) boolean async,
                                  Model model) {
        boolean flag = false;
        if (pageNum == -2){
            flag = true;
            pageNum = 0;
        }
        User user = userService.getUserByUserName(username);
        Page<EsBlog> blogs = null;
        try {
            if (order.equals("hot")) { // 最热查询
                blogs = esBlogService.listHotestEsBlogsByUsername(username,keyword, PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"comment","viewed")));
            } else if (order.equals("new")) { // 最新查询
                blogs = esBlogService.listNewestEsBlogsByUsername(username,keyword, PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
            }
        } catch (Exception e) {
            blogs = esBlogService.getAllEsBlogByUsername(username,PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
        }
        model.addAttribute("userModel",user);
        model.addAttribute("blogModel",blogs);
        if (flag == true && async == true) {
            return new ModelAndView("userspace/result", "model", model);
        }
        return new ModelAndView("userspace/resultList","model",model);
    }

}
