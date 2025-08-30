package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class MonthBalanceService {

    private final MonthBalanceRepository monthBalanceRepository;

    public MonthBalanceService(MonthBalanceRepository monthBalanceRepository) {
        this.monthBalanceRepository = monthBalanceRepository;
    }

    public MonthBalance getMonthBalance(Long userId, Long monthYear) {

        return monthBalanceRepository.findByUserIdAndMonthYear(userId, monthYear);
    }
}
