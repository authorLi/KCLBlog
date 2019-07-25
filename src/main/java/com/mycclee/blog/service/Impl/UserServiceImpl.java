package com.mycclee.blog.service.Impl;

import com.mycclee.blog.domain.User;
import com.mycclee.blog.repository.UserRepository;
import com.mycclee.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/10 21:08
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsersInBatch(List<User> users) {
        userRepository.deleteInBatch(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        String newName = "%" + name + "%";
        Page<User> users = userRepository.findByUsernameLike(newName,pageable);
        return users;
    }

    @Override
    public List<User> listUsersByUsernames(Collection<String> userNames) {
        return userRepository.findByUsernameIn(userNames);
    }
}
