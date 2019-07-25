package com.mycclee.blog;

import com.mycclee.blog.domain.Blog;
import com.mycclee.blog.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private BlogService blogService;

    @Test
    public void contextLoads() {
        Blog blog = blogService.getBlogById(15L);
    }

}
