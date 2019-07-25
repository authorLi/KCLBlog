package com.mycclee.blog.repository;

import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.domain.Catalog;
import com.mycclee.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/13 10:22
 */
public interface BlogRepository extends JpaRepository<Blog,Long> {

    Page<Blog> getAllByUser(User user, Pageable pageable);

    Page<Blog> getAllByCatalogOrderByCreateTimeDesc(Catalog catalog,Pageable pageable);
}
