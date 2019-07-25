package com.mycclee.blog.utils;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * @author mycclee
 * @createTime 2019/7/12 9:33
 */
public class ShiroUtils {

    private static final String HashAlgorithmName = "MD5";

    private static final int HashIterators = 1024;

    public static String md5(String credentials, String saltSource) {
        return new SimpleHash(HashAlgorithmName, credentials, ByteSource.Util.bytes(saltSource), HashIterators).toString();
    }

}
