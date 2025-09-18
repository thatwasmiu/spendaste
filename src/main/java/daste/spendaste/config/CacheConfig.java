package daste.spendaste.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Create RedisCacheManager first
        RedisCacheManager redisManager = RedisCacheManager.builder(redisConnectionFactory)
                .build();

        // Create SimpleCacheManager and register both types of caches
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("monthBalance"),
                new ConcurrentMapCache("weekTransaction"),
                redisManager.getCache("redisCache")
        ));

        return cacheManager;
    }

//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(10))         // default TTL 10 minutes
//                .disableCachingNullValues();
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }


//    @Bean
//    public CacheManager inMemoryCacheManager() {
//        return new ConcurrentMapCacheManager("localCache");
//    }

//    @Bean
//    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
//        return RedisCacheManager.builder(connectionFactory)
//                .build();
//    }

    // Optional: set one of them as the primary for @Cacheable
//    @Primary
//    @Bean
//    public CacheManager defaultCacheManager(RedisConnectionFactory connectionFactory) {
//        return RedisCacheManager.builder(connectionFactory).build();
//    }

}
