package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.core.model.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sd_month_spend")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthSpend {
    @Id
    Long monthYear;

    Long userId;

    Long digital;

    Long cash;

    Long budgetId;

    Long lastMonthSpend;
}
