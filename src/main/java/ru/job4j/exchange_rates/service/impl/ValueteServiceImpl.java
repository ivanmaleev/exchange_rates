package ru.job4j.exchange_rates.service.impl;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.job4j.exchange_rates.model.*;
import ru.job4j.exchange_rates.repository.RatesByDateRepository;
import ru.job4j.exchange_rates.repository.ValuteRepository;
import ru.job4j.exchange_rates.service.ValuteService;
import ru.job4j.exchange_rates.service.ValutesExchangeCounterService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ValueteServiceImpl implements ValuteService {

    public static final String VALUTES_API = "https://cbr.ru/scripts/XML_val.asp?d=0";
    public static final String RATES_API = "https://cbr.ru/scripts/XML_daily.asp?date_req=%s";
    public static final String DATE_FORMAT_PARAM = "dd/MM/yyyy";
    public static final String DATE_FORMAT_XML = "dd.MM.yyyy";

    private ValuteRepository valuteRepository;
    private RestTemplate rest;
    private RatesByDateRepository ratesByDateRepository;
    private DateFormatter dateFormatter;
    private ValutesExchangeCounterService counterService;

    @Override
    public void saveValutesFromXML(String valutesXML) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(ValutesDTO.class, Valute.class);
        ValutesDTO valutesDTO = null;

        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(valutesXML)) {
            valutesDTO = (ValutesDTO) unmarshaller.unmarshal(reader);
        }

        if (valutesDTO != null) {
            valuteRepository.saveAll(valutesDTO.getValutes());
        }
    }

    @Override
    public void saveRatesFromXML(String ratesXml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(RatesDTO.class, RateDTO.class);
        RatesDTO ratesDTO = null;

        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(ratesXml)) {
            ratesDTO = (RatesDTO) unmarshaller.unmarshal(reader);
        }
        if (ratesDTO != null) {
            List<RatesByDate> ratesByDate = getRatesByDateFromRatesDTO(ratesDTO);
            ratesByDateRepository.saveAll(ratesByDate);
        }
    }

    public List<RatesByDate> getRatesByDateFromRatesDTO(RatesDTO ratesDTO) {
        List<RatesByDate> ratesByDate = new ArrayList<>();
        ratesByDate = ratesDTO.getRateDTOList()
                .stream()
                .map((rateDTO) -> {
                    Valute valute = new Valute();
                    valute.setId(rateDTO.getId());
                    RatesByDate.DateValute dateValute = new RatesByDate.DateValute()
                            .setValute(valute).setDate(parseDate(ratesDTO.getDate()));
                    RatesByDate newRatesByDate = new RatesByDate()
                            .setDateValute(dateValute).setValue(
                                    Double.parseDouble(rateDTO.getValue().replace(",", ".")))
                            .setNominal(rateDTO.getNominal());
                    return newRatesByDate;
                }).collect(Collectors.toList());
        return ratesByDate;
    }

    @Override
    public void loadValutes() throws JAXBException {
        final String valutesXML = this.rest.getForObject(VALUTES_API, String.class);
        saveValutesFromXML(valutesXML);
    }

    @Override
    public void loadRatesByDate(Date date) throws JAXBException {
        dateFormatter.setPattern(DATE_FORMAT_PARAM);
        String dateStr = dateFormatter.print(date, new Locale("ru"));
        final String requestUrl = String.format(RATES_API, dateStr);
        final String ratesXML = this.rest.getForObject(requestUrl, String.class);
        saveRatesFromXML(ratesXML);
    }

    private Date parseDate(String dateString) {
        dateFormatter.setPattern(DATE_FORMAT_XML);
        try {
            return dateFormatter.parse(dateString, new Locale("ru"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    @Override
    public List<Valute> findAll() {
        return valuteRepository.findAll();
    }

    @Transactional
    @Override
    public double calculateExchage(String valuteFromId, String valuteToId, String countFrom) {
        RatesByDate ratesByDateFrom = findActualValuteValueById(valuteFromId);
        RatesByDate ratesByDateTo = findActualValuteValueById(valuteToId);
        if (ratesByDateFrom == null ||
                ratesByDateTo == null) {
            return 0;
        }
        counterService.incrementCounter(ratesByDateFrom, ratesByDateTo);
        return Integer.parseInt(countFrom) * (ratesByDateFrom.getValue() / ratesByDateFrom.getNominal())
                / (ratesByDateTo.getValue() / ratesByDateTo.getNominal());
    }

    private RatesByDate findActualValuteValueById(String valuteId) {
        Date currentDate = new Date(System.currentTimeMillis());
        Valute valuteFrom = valuteRepository.findById(valuteId).orElse(new Valute());
        final RatesByDate.DateValute dateValuteFrom = new RatesByDate.DateValute()
                .setValute(valuteFrom).setDate(currentDate);
        Optional<RatesByDate> ratesByDate = ratesByDateRepository.findById(dateValuteFrom);
        if (!ratesByDate.isPresent()) {
            try {
                loadRatesByDate(currentDate);
                ratesByDate = ratesByDateRepository.findById(dateValuteFrom);
                if (!ratesByDate.isPresent()) {
                    ratesByDate = ratesByDateRepository.
                            findDistinctFirstByDateValute_ValuteAndDateValute_DateBefore(valuteFrom, currentDate);
                    if (!ratesByDate.isPresent()) {
                        throw new NotFoundException("Valute not found");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return ratesByDate.get();
    }

    @Override
    public Map<String, Valute> findAllValutesToMap() {
        return findAll().stream()
                .collect(Collectors.toMap(Valute::getId
                        , v -> v, (v1, v2) -> v1, TreeMap::new));
    }
}
