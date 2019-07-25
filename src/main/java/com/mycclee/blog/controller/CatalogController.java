package com.mycclee.blog.controller;

import com.mycclee.blog.domain.Catalog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.ext.CatalogExt;
import com.mycclee.blog.service.CatalogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.utils.ConstraintViolationExceptionUtils;
import com.mycclee.blog.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

/**
 * @author mycclee
 * @createTime 2019/7/14 9:44
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    private static final Integer pageSize = 20;

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String catalogList(@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum,
                              @RequestParam(value = "username") String username, Model model) {
        User user = userService.getUserByUserName(username);
        Page<Catalog> page = catalogService.getCatalogPage(pageNum, pageSize, user);
        model.addAttribute("catalogs", page);
        model.addAttribute("username", username);
        model.addAttribute("isCatalogsOwner", SecurityUtils.getSubject().getPrincipal().equals(username) ? true : false);
        return "userspace/u :: #catalogReplace";
    }

    @PostMapping
    public ResponseEntity<ResponseUtils> addCatalog(@RequestBody CatalogExt catalogExt) {
        User user = userService.getUserByUserName(catalogExt.getUsername());
        Catalog catalog = catalogExt.getCatalog();
        catalog.setUser(user);
        try {
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok(new ResponseUtils(false, ConstraintViolationExceptionUtils.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseUtils(false, e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true, "添加成功", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtils> deleteCatalog(@PathVariable("id") Long id) {
        try {
            catalogService.removeCatalog(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok(new ResponseUtils(false, ConstraintViolationExceptionUtils.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseUtils(false, e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseUtils(true, "删除成功", null));
    }

    @GetMapping("/edit")
    public String getEditView(Model model) {
        Catalog catalog = new Catalog(null, null);
        model.addAttribute("catalog", catalog);
        return "userspace/catalogEdit";
    }

    @GetMapping("/edit/{id}")
    public String getCatalogById(@PathVariable("id") Long id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog", catalog);
        return "userspace/catalogEdit";
    }

}
