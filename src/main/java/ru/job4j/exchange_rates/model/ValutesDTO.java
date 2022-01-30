package ru.job4j.exchange_rates.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "Valuta")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValutesDTO {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Item")
    private List<Valute> valutes;
}
