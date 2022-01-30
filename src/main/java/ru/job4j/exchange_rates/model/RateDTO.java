package ru.job4j.exchange_rates.model;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
public class RateDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute(name = "ID")
    private String id;
    @XmlElement(name = "NumCode")
    private String numCode;
    @XmlElement(name = "CharCode")
    private String charCode;
    @XmlElement(name = "Nominal")
    private int nominal;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Value")
    private String value;
}
