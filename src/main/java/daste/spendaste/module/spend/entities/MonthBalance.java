package daste.spendaste.module.spend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import daste.spendaste.core.model.BaseEntity;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.models.MonthSpend;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "sd_month_balance")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthBalance extends BaseEntity {
    @Id
    Long monthYearUser;
    @NotNull
    Integer yearMonth;
    @NotNull
    Long userId;
    @NotNull
    BigDecimal digitalBalance = BigDecimal.ZERO;
    @NotNull
    BigDecimal cashBalance = BigDecimal.ZERO;
    @Embedded
    MonthBudget monthBudget;
    @Embedded
    MonthSpend monthSpend;

    @JsonIgnore
    public YearMonth getYearMonthType() {

        int year = Integer.parseInt(yearMonth.toString().substring(0, 4));
        int month = Integer.parseInt(yearMonth.toString().substring(4, 6));

        return YearMonth.of(year, month);
    }
}
