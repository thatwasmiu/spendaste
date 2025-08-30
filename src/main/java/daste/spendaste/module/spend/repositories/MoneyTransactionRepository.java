package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long> {

    List<MoneyTransaction> findByUserIdAndWeekYear(Long userId, Integer weekYear);
}
