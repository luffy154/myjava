package com.report.test.redis;

import com.alicp.jetcache.anno.*;
import org.springframework.stereotype.Service;

/**
 * @ClassName:UserService
 * @author: qm
 * @Description:
 * @date:2025-08-08
 */
@Service
public class UserService {
    @Cached(name="userCache-", key="#userId", expire = 3600, cacheType = CacheType.BOTH)
    User getUserById(long userId){
        User user = new User();
        user.setId(userId);
        user.setAge(0);
        user.setName("qm");
        return user;
    }

    @CacheUpdate(name="userCache-", key="#user.id", value="#user")
    void updateUser(User user){
        System.out.println("update user");
    }

    @CacheInvalidate(name="userCache-", key="#userId")
    void deleteUser(Long userId){
        System.out.println("delete user");
    }
}
