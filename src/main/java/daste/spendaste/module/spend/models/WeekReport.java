package daste.spendaste.module.spend.models;

import java.math.BigDecimal;

public interface WeekReport {
    String getYearWeek();
    Long getTotalCount();
    BigDecimal getCashIncluded();
    BigDecimal getCashExcluded();
    BigDecimal getDigitalIncluded();
    BigDecimal getDigitalExcluded();
}
