package com.mycclee.blog.repository.component;

import com.mycclee.blog.domain.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author mycclee
 * @createTime 2019/7/23 20:20
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,String> {

    EsBlog findByBlogId(Long blogId);

    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String summary, String content, String tags, Pageable pageable);

    Page<EsBlog> findAllByUsername(String username,Pageable pageable);

    Page<EsBlog> findDistinctEsBlogByUsernameAndTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String username,String title,String summary,String content,String tags,Pageable pageable);
}
