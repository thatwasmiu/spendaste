package daste.spendaste.module.spend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import daste.spendaste.core.model.BaseEntity;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.models.MonthReceive;
import daste.spendaste.module.spend.models.MonthSpend;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    MonthReceive monthReceive;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JdbcTypeCode(SqlTypes.JSON)
    List<Integer> weekOfMonth;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal currentCash() {
        return cashBalance.add(monthReceive.getCashReceive()).subtract(monthSpend.getCashSpend());
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal currentDigital() {
        return digitalBalance.add(monthReceive.getDigitalReceive()).subtract(monthSpend.getDigitalSpend());
    }

    @JsonIgnore
    public YearMonth getYearMonthType() {

        int year = Integer.parseInt(yearMonth.toString().substring(0, 4));
        int month = Integer.parseInt(yearMonth.toString().substring(4, 6));

        return YearMonth.of(year, month);
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
        this.weekOfMonth = getWeeksOfYearMonth();
    }

    private List<Integer> getWeeksOfYearMonth() {
        YearMonth ym = getYearMonthType();
        int year = ym.getYear();

        // 1. First day of year
        LocalDate firstOfYear = LocalDate.of(year, 1, 1);
        DayOfWeek dayOfWeek = firstOfYear.getDayOfWeek();

        // 2. Previous or same Monday
        int diffToMonday = (dayOfWeek == DayOfWeek.SUNDAY) ? -6 : DayOfWeek.MONDAY.getValue() - dayOfWeek.getValue();
        LocalDate firstMonday = firstOfYear.plusDays(diffToMonday);

        // 3. Iterate weeks until month ends
        LocalDate startOfMonth = ym.atDay(1);
        LocalDate endOfMonth = ym.atEndOfMonth();

        List<Integer> weeks = new ArrayList<>();
        LocalDate currentMonday = firstMonday;
        int weekNumber = 1;

        while (currentMonday.getYear() <= year || !currentMonday.isAfter(endOfMonth)) {
            LocalDate currentSunday = currentMonday.plusDays(6);

            // If this week overlaps with the month, include it
            if (!(currentSunday.isBefore(startOfMonth) || currentMonday.isAfter(endOfMonth))) {
                weeks.add(weekNumber);
            }

            // Move to next week
            currentMonday = currentMonday.plusWeeks(1);
            weekNumber++;
        }

        return weeks;
    }
}
