package com.mycclee.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author mycclee
 * @createTime 2019/7/22 9:16
 */
public final class RedisUtils {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public boolean expire(String key,Long time){
        try{
            if (time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void del(String... keys){
        if (keys != null&&keys.length > 0){
            if (keys.length == 1){
                redisTemplate.delete(keys[0]);
            }else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }


}
