package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
