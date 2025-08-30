package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MonthSpend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthSpendRepository extends JpaRepository<MonthSpend, Long> {
    MonthSpend findByUserIdAndMonthYear(Long userId, Long monthYear);
}
