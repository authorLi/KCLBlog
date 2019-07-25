package com.mycclee.blog.service.Impl;

import com.mycclee.blog.domain.Catalog;
import com.mycclee.blog.domain.User;
import com.mycclee.blog.repository.CatalogRepository;
import com.mycclee.blog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/14 9:52
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Page<Catalog> getCatalogPage(Integer pageNum, Integer pageSize, User user) {
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        return catalogRepository.findByUser(user, pageable);
    }

    @Override
    public List<Catalog> getCatalogList(User user) {
        return catalogRepository.findByUser(user);
    }

    @Override
    public void saveCatalog(Catalog catalog) {
        Catalog sqlCatalog = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (sqlCatalog != null) {
            throw new IllegalArgumentException("该分类已存在");
        }
        //字符串出现的第一个字母大写
        char[] cs = catalog.getName().toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] >= 65 && cs[i] <= 90) {
                break;
            } else if (cs[i] >= 97 && cs[i] <= 122) {
                cs[i] -= 32;
                break;
            }
        }
        catalog.setName(String.valueOf(cs));
        catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.getOne(id);
    }
}
