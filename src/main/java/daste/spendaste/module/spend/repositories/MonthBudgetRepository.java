package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MonthBudget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthBudgetRepository extends JpaRepository<MonthBudget, Long> {
    MonthBudget findByUserIdAndMonthYear(Long userId, Long monthYear);
}
