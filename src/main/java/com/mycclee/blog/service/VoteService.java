package com.mycclee.blog.service;

import com.mycclee.blog.domain.User;

/**
 * @author mycclee
 * @createTime 2019/7/25 14:35
 */
public interface VoteService {

    void createVote(User user, Long blogId);

    boolean hasUser(User user, Long blogId);

    void removeVote(User user, Long blogId);

    void addViewed(User user, Long blogId);

    void decreaseViewed(User user,Long blogId);

    boolean viewedHasUser(User user, Long blogId);
}
