package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionMethod;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class WeekSpend {
    private final Collection<DayTransaction> dayTransactions;
    private BigDecimal cashSpend = BigDecimal.ZERO;
    private BigDecimal digitalSpend = BigDecimal.ZERO;
    private BigDecimal digitalReceive = BigDecimal.ZERO;
    private BigDecimal cashReceive = BigDecimal.ZERO;

    public WeekSpend(Integer yearWeek, List<MoneyTransaction> transactions) {
        Map<Integer, DayTransaction> daySpendMap = getDaySpendMap(yearWeek);
        setSpending(transactions, daySpendMap);
        dayTransactions = daySpendMap.values();
    }

    private Map<Integer, DayTransaction> getDaySpendMap(Integer yearWeek) {
        int year = yearWeek / 100;
        int week = yearWeek % 100;

        WeekFields weekFields = WeekFields.ISO;

        // First day of that week
        LocalDate firstDayOfWeek = LocalDate.ofYearDay(year, 1)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);

        return Stream.of(DayOfWeek.values())
                        .collect(Collectors.toMap(
                                DayOfWeek::getValue,
                                d -> {
                                    LocalDate date = firstDayOfWeek.plusDays(d.getValue() - 1);
                                    return new DayTransaction(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
                                },
                                (a, b) -> a, LinkedHashMap::new
                        ));
    }

    private void setSpending(List<MoneyTransaction> transactions, Map<Integer, DayTransaction> dayTransactionMap) {
        transactions.forEach(transaction -> {
            DayTransaction dayTransaction = dayTransactionMap.get(transaction.getDayOfWeek());
            dayTransaction.doTransaction(transaction);
        });
        dayTransactionMap.values().forEach(dayTransaction -> {
            this.cashSpend = this.cashSpend.add(dayTransaction.getCashSpend());
            this.digitalSpend = this.digitalSpend.add(dayTransaction.getDigitalSpend());
            this.cashReceive = this.cashReceive.add(dayTransaction.getCashReceive());
            this.digitalReceive = this.digitalReceive.add(dayTransaction.getDigitalReceive());
        });
    }
}
