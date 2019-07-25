package com.mycclee.blog.service.Impl;

import com.mycclee.blog.domain.Comment;
import com.mycclee.blog.repository.CommentRepository;
import com.mycclee.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mycclee
 * @createTime 2019/7/14 22:03
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
