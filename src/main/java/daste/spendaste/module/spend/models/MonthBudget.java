package daste.spendaste.module.spend.models;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class MonthBudget implements Serializable {
    BigDecimal budget;
    BigDecimal extraIncrease;
    String increaseReason;

    public MonthBudget(MonthSpend monthSpend) {
        this.budget = monthSpend.totalSpend();
    }

    public boolean notAvailable() {
        return budget == null;
    }
}
