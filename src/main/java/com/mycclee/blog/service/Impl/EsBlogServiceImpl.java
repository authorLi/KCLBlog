package com.mycclee.blog.service.Impl;

import com.mycclee.blog.domain.EsBlog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.repository.component.EsBlogRepository;
import com.mycclee.blog.service.EsBlogService;
import com.mycclee.blog.service.UserService;
import com.mycclee.blog.utils.TagUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * @author mycclee
 * @createTime 2019/7/23 20:44
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {

    @Autowired
    private EsBlogRepository esBlogRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private UserService userService;

//    private static final Pageable TOP_5_PAGEABLE = PageRequest.of(0, 5);
//    private static final String EMPTY_KEYWORD = "";

    @Override
    public void removeEsBlog(String id) {
        esBlogRepository.deleteById(id);
    }

    @Override
    public EsBlog updateEsBlog(EsBlog esBlog) {
        return esBlogRepository.save(esBlog);
    }

    @Override
    public EsBlog getEsBlogByBlogId(Long blogId) {
        return esBlogRepository.findByBlogId(blogId);
    }

    @Override
    public Page<EsBlog> getAllEsBlogByUsername(String username, Pageable pageable) {
        return esBlogRepository.findAllByUsername(username,pageable);
    }

    @Override
    public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) {
        Page<EsBlog> page = null;
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        if (pageable.getSort() == null){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword,pageable);
        return page;
    }

    @Override
    public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) {
        Sort sort = new Sort(Sort.Direction.DESC,"viewed","comment","createTime");
        if (pageable.getSort() == null){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword,pageable);
    }

    @Override
    public Page<EsBlog> listNewestEsBlogsByUsername(String username, String keyword, Pageable pageable) {
        Page<EsBlog> page = null;
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        if (pageable.getSort() == null){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        page = esBlogRepository.findDistinctEsBlogByUsernameAndTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(username,keyword,keyword,keyword,keyword,pageable);
        return page;
    }

    @Override
    public Page<EsBlog> listHotestEsBlogsByUsername(String username, String keyword, Pageable pageable) {
        Page<EsBlog> page = null;
        Sort sort = new Sort(Sort.Direction.DESC,"comment","viewed");
        if (pageable.getSort() == null){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        page = esBlogRepository.findDistinctEsBlogByUsernameAndTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(username,keyword,keyword,keyword,keyword,pageable);
        return  page;
    }

    @Override
    public Page<EsBlog> listEsBlogs(Pageable pageable) {
        return esBlogRepository.findAll(pageable);
    }

    @Override
    public List<EsBlog> listTop5NewestEsBlogs() {
        return esBlogRepository.findAll(PageRequest.of(0,5,new Sort(Sort.Direction.DESC,"createTime"))).getContent();
    }

    @Override
    public List<EsBlog> listTop5HotestEsBlogs() {
        return esBlogRepository.findAll(PageRequest.of(0,5,new Sort(Sort.Direction.DESC,"viewed","comment"))).getContent();
    }

    @Override
    public List<User> listTop12Users() {
        List<String> usernameList = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("blog").withTypes("blog")
                .addAggregation(terms("users").field("username").order(BucketOrder.count(false)).size(12))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("users");
        Iterator<StringTerms.Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()){
            StringTerms.Bucket bucket = modelBucketIt.next();
            String username = bucket.getKey().toString();
            usernameList.add(username);
        }
        List<User> list = userService.listUsersByUsernames(usernameList);
        return list;
    }

    @Override
    public List<TagUtils> listTop30Tags() {

        List<TagUtils> list = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("blog").withTypes("blog")
                .addAggregation(terms("tags").field("tags").order(BucketOrder.count(false)).size(30))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags");

        Iterator<StringTerms.Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            StringTerms.Bucket actiontypeBucket = modelBucketIt.next();

            list.add(new TagUtils(actiontypeBucket.getKey().toString(),
                    actiontypeBucket.getDocCount()));
        }
        return list;
    }
}
