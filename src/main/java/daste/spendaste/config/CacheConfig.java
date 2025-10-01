package daste.spendaste.config;

import daste.spendaste.core.security.SecurityUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

//    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Create RedisCacheManager first
        RedisCacheManager redisManager = RedisCacheManager.builder(redisConnectionFactory)
                .build();

        // Create SimpleCacheManager and register both types of caches
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
//                new ConcurrentMapCache("monthBalance"),
                new ConcurrentMapCache("weekSpend"),
                redisManager.getCache("monthBalance")
        ));

        return cacheManager;
    }

//    @Bean("tenantCacheResolver") // work with ConcurrentMapCacheManager (dynamic cache) only
//    public CacheResolver tenantCacheResolver(CacheManager cacheManager) {
//        return context -> {
//            Long tenantId = SecurityUtils.getTenant();
//
//            return context.getOperation().getCacheNames().stream()
//                    .map(name -> cacheManager.getCache(tenantId + ":" + name))
//                    .toList();
//        };
//    }

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

//    @Bean
//    public CacheManager defaultCacheManager(RedisConnectionFactory factory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer())
//                );
//        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
//    }
//
//    @Bean
//    public CacheManager jsonCacheManager(RedisConnectionFactory factory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                );
//        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
//    }


//    @Bean
//    public CacheManager binaryRedisCacheManager(RedisConnectionFactory factory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer())
//                );
//        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
//    }
//
//    @Bean
//    public CacheManager jsonRedisCacheManager(RedisConnectionFactory factory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                );
//        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
//    }
//
//    @Bean
//    public CacheManager simpleCacheManager() {
//        SimpleCacheManager manager = new SimpleCacheManager();
//        manager.setCaches(Arrays.asList(new ConcurrentMapCache("weekSpend")));
//        return manager;
//    }
}
