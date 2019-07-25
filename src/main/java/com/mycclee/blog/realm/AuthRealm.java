package com.mycclee.blog.realm;

import com.mycclee.blog.domain.User;
import com.mycclee.blog.repository.UserRepository;
import com.mycclee.blog.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author mycclee
 * @createTime 2019/7/10 20:58
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();

        if (userName.equals("admin")){

            List<String> permissions=new ArrayList<>();
            List<String> roles =new ArrayList<>();
            permissions.add("add");
            roles.add("superAdmin");
            //组装返回数据
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRoles(roles);
            simpleAuthorizationInfo.addStringPermissions(permissions);
            try {
                //不确定是什么原因导致权限可能会生成一个空值"", 会报错,所以将空值删除
                if (simpleAuthorizationInfo != null && simpleAuthorizationInfo.getStringPermissions() != null) {
                    Set<String> perms = simpleAuthorizationInfo.getStringPermissions();
                    for (String permission : perms) {
                        if (StringUtils.isEmpty(permission)) {
                            permissions.remove(permission);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("移除空值权限出错---"+e.getMessage());
            }
            return simpleAuthorizationInfo;
        }else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UnknownAccountException("用户不存在,请先注册");
        }
        Subject curUser = SecurityUtils.getSubject();
//        curUser.getSession().setAttribute("username",username);
        if (user.getPassword().equals(ShiroUtils.md5(password,username))){
            System.out.println("登陆成功");
        }else {
            System.out.println("出错了");
            throw new UnknownAccountException("用户名或密码错误");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,
                ByteSource.Util.bytes(username),getName());
        return info;
    }
}
