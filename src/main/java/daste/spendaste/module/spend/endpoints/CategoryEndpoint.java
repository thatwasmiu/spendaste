package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.Category;
import daste.spendaste.module.spend.services.CategoryService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sp/api/aaa")
public class CategoryEndpoint {

    private final CategoryService categoryService;

    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PutMapping("create")
    public Category createMoneyTransaction(@RequestBody Category category) {
        Long userId = 0L;
        return categoryService.createCategory(userId, category);
    }
}
