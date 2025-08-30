package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.core.model.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sd_month_balance")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthBalance {
    @Id
    Long monthYear;

    Long userId;
    Long digitalStart;

    Long digitalCurrent;

    Long cashStart;

    Long cashCurrent;
}
