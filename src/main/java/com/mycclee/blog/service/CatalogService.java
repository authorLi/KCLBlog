package com.mycclee.blog.service;

import com.mycclee.blog.domain.Catalog;
import com.mycclee.blog.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/14 9:50
 */
public interface CatalogService {

    Page<Catalog> getCatalogPage(Integer pageNum, Integer pageSize, User user);

    List<Catalog> getCatalogList(User user);

    void saveCatalog(Catalog catalog);

    void removeCatalog(Long id);

    Catalog getCatalogById(Long id);
}
