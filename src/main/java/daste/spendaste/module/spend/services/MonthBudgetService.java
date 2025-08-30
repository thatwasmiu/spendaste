package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MonthBudget;
import daste.spendaste.module.spend.repositories.MonthBudgetRepository;
import org.springframework.stereotype.Service;

@Service
public class MonthBudgetService {

    private final MonthBudgetRepository monthBudgetRepository;

    public MonthBudgetService(MonthBudgetRepository monthBudgetRepository) {
        this.monthBudgetRepository = monthBudgetRepository;
    }

    public MonthBudget getMonthBudget(Long userId, Long monthYear) {

        return monthBudgetRepository.findByUserIdAndMonthYear(userId, monthYear);
    }
}
