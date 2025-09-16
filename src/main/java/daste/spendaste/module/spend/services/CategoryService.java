package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.Category;
import daste.spendaste.module.spend.repositories.CategoryRepository;
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
}
