package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long> {

    List<MoneyTransaction> findByYearWeek(Integer yearWeek);

    List<MoneyTransaction> findByYearMonth(Integer monthYear);

    List<MoneyTransaction> findByYearMonthAndTypeIn(Integer yearMonth, List<TransactionType> types);
}
