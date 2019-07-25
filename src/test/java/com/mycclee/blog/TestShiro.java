package com.mycclee.blog;

import com.mycclee.blog.utils.ShiroUtils;
import org.junit.Test;

/**
 * @author mycclee
 * @createTime 2019/7/12 10:52
 */
public class TestShiro {

    @Test
    public void testMd5(){
        String result = ShiroUtils.md5("123456","mycclee");
        System.out.println(result);
    }
}
