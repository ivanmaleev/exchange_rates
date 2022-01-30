package ru.job4j.exchange_rates.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "rates")
@NoArgsConstructor
@Accessors(chain = true)
public class RatesByDate {

    @EmbeddedId
    private DateValute dateValute;
    @XmlElement(name = "Value")
    private double value;
    @XmlElement(name = "Nominal")
    private double nominal;

    @Embeddable
    @NoArgsConstructor
    @Accessors(chain = true)
    @Data
    public static class DateValute implements Serializable, Comparable {
        @Temporal(TemporalType.DATE)
        private Date date;
        @ManyToOne
        private Valute valute;

        @Override
        public int compareTo(Object o) {
            DateValute o1 = (DateValute) o;
            if (valute == o1.valute) {
                return date.compareTo(o1.date);
            }
            return valute.getId().compareTo(o1.valute.getId());
        }
    }
}
