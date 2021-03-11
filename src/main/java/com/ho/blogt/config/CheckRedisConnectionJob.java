package com.ho.blogt.config;

import com.ho.blogt.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckRedisConnectionJob {
    @Autowired
    RedisServiceImpl redisService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void checkRedisConnectionJob() {
        try {
            redisService.checkConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
