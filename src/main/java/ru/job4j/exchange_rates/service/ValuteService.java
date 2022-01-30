package ru.job4j.exchange_rates.service;

import ru.job4j.exchange_rates.model.Valute;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.JAXBException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public interface ValuteService {

    void saveValutesFromXML(String xml) throws JAXBException;

    void saveRatesFromXML(String xml) throws JAXBException;

    void loadValutes() throws JAXBException;

    void loadRatesByDate(Date date) throws JAXBException;

    List<Valute> findAll();

    double calculateExchage(@NotBlank String valuteFromId, @NotBlank String valuteToId
            ,@NotBlank String countFrom);

    public Map<String, Valute> findAllValutesToMap();
}


