package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.Category;
import daste.spendaste.module.spend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return null;
    }

    @Autowired
    private CacheManager cacheManager;

    public void debugCaches() {
        System.out.println("Available caches: " + cacheManager.getCacheNames());
        Cache cache = cacheManager.getCache("monthBalance");
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cacheManager.getCache("monthBalance");
        System.out.println(cache.getNativeCache()); // shows the backing map
        if (cache != null) {
            System.out.println("monthBalance cache is present.");
        } else {
            System.out.println("monthBalance cache is missing.");
        }
    }
}
