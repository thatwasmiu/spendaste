package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionType;
import daste.spendaste.module.spend.models.MonthTransactionRatio;
import daste.spendaste.module.spend.models.WeekReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long> {

    List<MoneyTransaction> findByYearWeek(Integer yearWeek);

    List<MoneyTransaction> findByYearMonth(Integer monthYear);

    List<MoneyTransaction> findByYearMonthAndTypeIn(Integer yearMonth, List<TransactionType> types);

    @Query(value = """
        SELECT
            year_week AS yearWeek,
            COUNT(*) AS totalCount,
            COALESCE(SUM(amount) FILTER (WHERE method = 0 and type in (0, 2)), 0)  AS cashIncluded,
        		COALESCE(SUM(amount) FILTER (WHERE method = 0 and type = 1), 0)  AS cashExcluded,
            COALESCE(SUM(amount) FILTER (WHERE method = 1 and type in (0, 2)), 0) AS digitalIncluded,
        		COALESCE(SUM(amount) FILTER (WHERE method = 1 and type = 1), 0) AS digitalExcluded
        FROM sd_money_transaction
        WHERE type IN (0, 1, 2) and year_week in :yearWeek
        GROUP BY year_week
        ORDER BY year_week;
        """, nativeQuery = true)
    List<WeekReport> findWeeklyTransactions(@Param("yearWeek") Collection<Integer> yearWeek);

    @Query(value = """
        SELECT
            totalCount,
            spendIncluded,
            receiveIncluded,
            ROUND(spendIncluded / NULLIF(receiveIncluded, 0), 2) AS spendToReceive,
            ROUND(receiveIncluded / NULLIF(spendIncluded, 0), 2) AS receiveToSpend
        FROM (
            SELECT\s
                COUNT(*) AS totalCount,
                COALESCE(SUM(amount) FILTER (WHERE type IN (0, 2)), 0) AS spendIncluded,
                COALESCE(SUM(amount) FILTER (WHERE type IN (3, 5)), 0) AS receiveIncluded
            FROM sd_money_transaction
            WHERE type IN (0, 2, 3, 5) and year_month = :yearMonth
        ) t;
        """, nativeQuery = true)
    MonthTransactionRatio findTransactionRatio(@Param("yearMonth") Integer yearMonth);
}
