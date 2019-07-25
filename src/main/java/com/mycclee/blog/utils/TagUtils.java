package com.mycclee.blog.utils;

import java.io.Serializable;

/**
 * @author mycclee
 * @createTime 2019/7/25 14:10
 */
public class TagUtils implements Serializable {

    private String name;

    private Long count;

    public TagUtils(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
