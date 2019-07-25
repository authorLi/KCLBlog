package com.mycclee.blog.repository;

import com.mycclee.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mycclee
 * @createTime 2019/7/14 21:58
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
