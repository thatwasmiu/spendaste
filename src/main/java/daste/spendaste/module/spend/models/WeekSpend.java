package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionMethod;

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

public class WeekSpend {
    private final Collection<DaySpend> daySpends;
    private BigDecimal cashSpend = BigDecimal.ZERO;
    private BigDecimal digitalSpend = BigDecimal.ZERO;

    public WeekSpend(Integer yearWeek, List<MoneyTransaction> transactions) {
        Map<Integer, DaySpend> daySpendMap = getDaySpendMap(yearWeek);
        setSpending(transactions, daySpendMap);
        daySpends = daySpendMap.values();
    }

    private Map<Integer, DaySpend> getDaySpendMap(Integer yearWeek) {
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
                                    return new DaySpend(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
                                },
                                (a, b) -> a, LinkedHashMap::new
                        ));
    }

    private void setSpending(List<MoneyTransaction> transactions, Map<Integer, DaySpend> daySpendMap) {
        transactions.forEach(transaction -> {
            DaySpend daySpend = daySpendMap.get(transaction.getDayOfWeek());
            daySpend.addSpending(transaction);
        });
        daySpendMap.values().forEach(daySpend -> {
            this.cashSpend = this.cashSpend.add(daySpend.getCashSpend());
            this.digitalSpend = this.digitalSpend.add(daySpend.getDigitalSpend());
        });
    }

    public Collection<DaySpend> getDaySpends() {
        return daySpends;
    }

    public BigDecimal getCashSpend() {
        return cashSpend;
    }

    public BigDecimal getDigitalSpend() {
        return digitalSpend;
    }
}
