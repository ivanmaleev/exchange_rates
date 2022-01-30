package ru.job4j.exchange_rates.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Data
public class Valute implements Serializable {

    @XmlAttribute(name = "ID")
    @Id
    private String id;

    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "EngName")
    private String engName;
    @XmlElement(name = "Nominal")
    private int nominal;
    @XmlElement(name = "ParentCode")
    private String parentCode;
}
