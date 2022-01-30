package ru.job4j.exchange_rates.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ValuteStatisticDTO {
    private Valute valuteFrom;
    private Valute valuteTo;
    private long count;
}
