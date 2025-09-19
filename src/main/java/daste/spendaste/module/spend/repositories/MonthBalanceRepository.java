package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MonthBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonthBalanceRepository extends JpaRepository<MonthBalance, Long> {
    Optional<MonthBalance> findByYearMonth(int monthYear);
}
