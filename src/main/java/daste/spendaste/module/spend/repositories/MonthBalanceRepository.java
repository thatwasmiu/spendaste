package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MonthBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthBalanceRepository extends JpaRepository<MonthBalance, Long> {
    MonthBalance findByUserIdAndMonthYear(Long userId, Long monthYear);
}
