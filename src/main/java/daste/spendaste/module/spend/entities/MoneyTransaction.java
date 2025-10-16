package daste.spendaste.module.spend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.module.spend.enums.TransactionMethod;
import daste.spendaste.module.spend.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

@Entity
@Table(name = "sd_money_transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class MoneyTransaction extends BaseIdEntity {
    String name;
    TransactionType type = TransactionType.INCOMING_PENDING;
    @NotNull
    Long userId;
    @NotNull
    TransactionMethod method;
    @NotNull
    Long date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer yearMonth;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer yearWeek;
    BigDecimal amount;
    Long categoryId;

    public void setDate(@NotNull Long date) {
        this.date = date;
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(zone).toLocalDate();

        this.yearMonth = localDate.getYear() * 100 + localDate.getMonthValue();
        LocalDate firstMonday = LocalDate.of(localDate.getYear(), 1, 1)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        long daysSinceFirstMonday = ChronoUnit.DAYS.between(firstMonday, localDate);
        int weekOfYear = (int) (daysSinceFirstMonday / 7) + 1;

        this.yearWeek = localDate.getYear() * 100 + weekOfYear;
    }

    public static void main(String[] args) {
        Long date = 1798637837000L;

        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(zone).toLocalDate();

        // YearMonth as Integer (yyyyMM)
        System.out.println(localDate.getYear() * 100 + localDate.getMonthValue());
        // Calendar-year week (weeks start on Monday)
        // Find the first Monday of the year
        LocalDate firstMonday = LocalDate.of(localDate.getYear(), 1, 1)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        long daysSinceFirstMonday = ChronoUnit.DAYS.between(firstMonday, localDate);
        System.out.println(daysSinceFirstMonday);
        int weekOfYear = (int) (daysSinceFirstMonday / 7) + 1;

        System.out.println(localDate.getYear() * 100 + weekOfYear);
    }

    @JsonIgnore
    @Transient
    public Integer getDayOfWeek() {
        Instant instant = Instant.ofEpochMilli(date);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    @JsonIgnore
    public Boolean isSpending() {
        return TransactionType.spendingTypes().contains(type);
    }

    @JsonIgnore
    public Boolean isReceiving() {
        return TransactionType.receivingTypes().contains(type);
    }
}
