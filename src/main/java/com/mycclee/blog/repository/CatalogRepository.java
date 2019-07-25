package com.mycclee.blog.repository;

import com.mycclee.blog.domain.Catalog;
import com.mycclee.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/14 9:53
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Page<Catalog> findByUser(User user, Pageable pageable);

    List<Catalog> findByUser(User user);

    Catalog findByUserAndName(User user, String name);
}
