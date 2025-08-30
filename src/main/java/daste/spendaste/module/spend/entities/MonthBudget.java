package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.BaseEntity;
import daste.spendaste.core.model.BaseIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sd_month_budget")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthBudget extends BaseEntity {

    @Id
    Long monthYear;

    Long total;

    Long remain;

    Long extraIncrease;

    String increaseReason;
}
