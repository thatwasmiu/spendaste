package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.models.MonthTransactionRatio;
import daste.spendaste.module.spend.models.WeekReport;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyReportService {

    private final MoneyTransactionRepository moneyTransactionRepository;


    public MoneyReportService(MoneyTransactionRepository moneyTransactionRepository) {
        this.moneyTransactionRepository = moneyTransactionRepository;
    }

    public List<WeekReport> get5WeekReport(Integer yearWeek) {
        List<Integer> weeks = getLastNWeeks(yearWeek, 5);
        return moneyTransactionRepository.findWeeklyTransactions(weeks);
    }

    public static List<Integer> getLastNWeeks(int yearWeek, int n) {
        int year = yearWeek / 100;
        int week = yearWeek % 100;

        // Use ISO week date (Monday-based weeks)
        WeekFields wf = WeekFields.ISO;

        // Start from the Monday of that week
        LocalDate date = LocalDate.ofYearDay(year, 1)
                .with(wf.weekOfYear(), week)
                .with(wf.dayOfWeek(), 1);

        List<Integer> result = new ArrayList<>();

        for (int i = n - 1; i >= 0; i--) {
            LocalDate d = date.minusWeeks(i);
            int y = d.get(wf.weekBasedYear());
            int w = d.get(wf.weekOfYear());
            result.add(y * 100 + w);
        }

        return result;
    }

    public MonthTransactionRatio getTransactionRatio(Integer yearMonth) {
        return moneyTransactionRepository.findTransactionRatio(yearMonth);
    }
}
