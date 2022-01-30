package ru.job4j.exchange_rates.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "exchange_counter")
@Data
@Accessors(chain = true)
public class ValutesExchangeCounter {

    @EmbeddedId
    private DateValutes dateValutes;
    private double valueFrom;
    private double valueTo;
    private long counter;

    @Embeddable
    @NoArgsConstructor
    @Accessors(chain = true)
    @Data
    public static class DateValutes implements Serializable{
        @ManyToOne
        private Valute valuteFrom;
        @ManyToOne
        private Valute valuteTo;
        @Temporal(TemporalType.DATE)
        private Date date;
    }
}
