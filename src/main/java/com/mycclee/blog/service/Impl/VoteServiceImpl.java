package com.mycclee.blog.service.Impl;

import com.mycclee.blog.domain.User;
import com.mycclee.blog.service.VoteService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mycclee
 * @createTime 2019/7/25 14:36
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public void createVote(User user, Long blogId) {
        redisTemplate.opsForSet().add("blogVote:" + blogId,user);
    }

    @Override
    public boolean hasUser(User user, Long blogId) {
        return redisTemplate.opsForSet().isMember("blogVote:" + blogId,user);
    }

    @Override
    public void removeVote(User user, Long blogId) {
        redisTemplate.opsForSet().remove("blogVote:" + blogId,user);
    }

    @Override
    public void addViewed(User user, Long blogId) {
        redisTemplate.opsForSet().add("blogViewed:" + blogId,user);
    }

    @Override
    public void decreaseViewed(User user, Long blogId) {
        redisTemplate.opsForSet().remove("blogViewed:" + blogId,user);
    }

    @Override
    public boolean viewedHasUser(User user, Long blogId) {
        return redisTemplate.opsForSet().isMember("blogViewed:" + blogId,user);
    }
}
