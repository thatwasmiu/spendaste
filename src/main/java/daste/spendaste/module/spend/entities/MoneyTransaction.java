package daste.spendaste.module.spend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.module.spend.enums.TransactionMethod;
import daste.spendaste.module.spend.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private Integer yearMonth;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer yearWeek;
    BigDecimal amount;
    Long categoryId;

    public void setDate(Long date) {
        this.date = date;
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dateTime = Instant.ofEpochMilli(date).atZone(zone).toLocalDateTime();
        // YearMonth as Integer (yyyyMM)
        this.yearMonth = dateTime.getYear() * 100 + dateTime.getMonthValue();
        // YearWeek as Integer (yyyyWW)
        WeekFields weekFields = WeekFields.ISO; // ISO week numbering
        int weekYear = dateTime.get(weekFields.weekBasedYear());
        int weekOfYear = dateTime.get(weekFields.weekOfWeekBasedYear());
        this.yearWeek = weekYear * 100 + weekOfYear;
    }

    @JsonIgnore
    public Boolean isSpending() {
        return TransactionType.OUTGOING_INCLUDED.equals(type) || TransactionType.OUTGOING_PENDING.equals(type);
    }
}
