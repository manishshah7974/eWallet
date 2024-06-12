package com.fintech.eWallet.repository;

import com.fintech.eWallet.model.Kyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class KycRedisRepository {

    private static final String KEY_PREFIX = "KYC";
    private static final long EXPIRATION_DAYS = 1;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(Kyc kyc) {
        String userIdKey = getKey(kyc.getUserId());
        redisTemplate.opsForHash().put(KEY_PREFIX, userIdKey, kyc);
        redisTemplate.expire(KEY_PREFIX, EXPIRATION_DAYS, TimeUnit.DAYS); // Expire data after 1 Day
    }

    public Kyc findByUserId(Long userId) {
        String userIdKey = getKey(userId);
        return (Kyc) redisTemplate.opsForHash().get(KEY_PREFIX, userIdKey);
    }

    public boolean exists(Long userId) {
        String userIdKey = getKey(userId);
        return redisTemplate.opsForHash().hasKey(KEY_PREFIX, userIdKey);
    }

    private String getKey(Long userId) {
        return KEY_PREFIX + ":" + userId;
    }
}
