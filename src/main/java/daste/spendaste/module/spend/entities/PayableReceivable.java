package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.core.model.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sd_payable_receivable")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayableReceivable extends IdEntity {
    Integer type;

    String source;

    Boolean payed;

    Long userId;

    Long startDate;

    Long endDate;
}
