package daste.spendaste.module.spend.models;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class MonthBudget {
    BigDecimal budget;
    BigDecimal extraIncrease;
    String increaseReason;

    public MonthBudget(MonthSpend monthSpend) {
        this.budget = monthSpend.totalSpend();
    }
}
