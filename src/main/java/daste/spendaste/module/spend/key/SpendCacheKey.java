package daste.spendaste.module.spend.key;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MoneyTransaction;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpendCacheKey {
    @Bean("tenantMonthBalance")
    public KeyGenerator tenantYearMonth() {
        return (target, method, params) -> generateKeyStr(params[0]);
    }

    @Bean("tenantWeekSpend")
    public KeyGenerator tenantWeekSpend() {
        return (target, method, params) -> generateKeyStr(params[0]);
    }

    @Bean("tenantWeekSpendTransaction")
    public KeyGenerator tenantTransaction() {
        return (target, method, params) -> {
            Integer yearWeek = ((MoneyTransaction) params[0]).getYearWeek();
            return generateKeyStr(yearWeek);
        };
    }

    public String generateKey(Object... params) {
        String tenantId = SecurityUtils.getTenant();
        return tenantId + ":" + Arrays.deepToString(params);
    }

    public String generateKeyStr(Object value) {
        String tenantId = SecurityUtils.getTenant();
        return tenantId + ":" + value;
    }
}
