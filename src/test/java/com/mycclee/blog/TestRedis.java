package com.mycclee.blog;

import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.repository.BlogRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author mycclee
 * @createTime 2019/7/16 10:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private BlogRepository blogRepository;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    public TestRedis(){
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

//    @Test
//    public void test() throws Exception{
//        System.setProperty("es.set.netty.runtime.available.processors","false");
//        redisTemplate.opsForValue().set("aaa","111");
//        Assert.assertEquals("111",redisTemplate.opsForValue().get("aaa"));
//    }

    @Test
    public void test1(){
//        redisTemplate.opsForValue().set("ccc", PageRequest.of(1,5));
//        Assert.assertEquals("bbb",redisTemplate.opsForValue().get("bbb"));
        PageRequest pageAble = PageRequest.of(1, 5, Sort.Direction.DESC,"createTime");

        Page<Blog> blogs = blogRepository.findAll(pageAble);
        redisTemplate.opsForValue().set("blogs",blogs);
//        Assert.assertEquals(blogs,redisTemplate.opsForValue().get("blogs"));
    }


}
