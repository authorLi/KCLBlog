package com.mycclee.blog.repository;

import com.mycclee.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/10 21:05
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    Page<User> findByUsernameLike(String name, Pageable pageable);

    List<User> findByUsernameIn(Collection<String> usernames);
}
