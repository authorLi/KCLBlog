package com.mycclee.blog.service;

import com.mycclee.blog.domain.Comment;

/**
 * @author mycclee
 * @createTime 2019/7/14 22:02
 */
public interface CommentService {

    Comment getCommentById(Long id);

    void removeComment(Long id);

    Comment addComment(Comment comment);
}
