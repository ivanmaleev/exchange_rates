package ru.job4j.exchange_rates.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.exchange_rates.model.RatesByDate;
import ru.job4j.exchange_rates.model.Valute;
import ru.job4j.exchange_rates.model.ValuteStatisticDTO;
import ru.job4j.exchange_rates.model.ValutesExchangeCounter;
import ru.job4j.exchange_rates.repository.ValutesExchangeCounterRepository;
import ru.job4j.exchange_rates.service.ValutesExchangeCounterService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class ValutesExchangeCounterServiceImpl implements ValutesExchangeCounterService {

    private ValutesExchangeCounterRepository counterRepository;

    @Transactional
    @Override
    public void incrementCounter(RatesByDate ratesByDateFrom, RatesByDate ratesByDateTo) {
        Date currentDate = new Date(System.currentTimeMillis());
        ValutesExchangeCounter.DateValutes dateValutes = new ValutesExchangeCounter.DateValutes()
                .setDate(currentDate)
                .setValuteFrom(ratesByDateFrom.getDateValute().getValute())
                .setValuteTo(ratesByDateTo.getDateValute().getValute());
        Optional<ValutesExchangeCounter> counterById = counterRepository.findById(dateValutes);
        if (!counterById.isPresent()) {
            ValutesExchangeCounter valutesExchangeCounter = new ValutesExchangeCounter()
                    .setDateValutes(dateValutes)
                    .setValueFrom(ratesByDateFrom.getValue())
                    .setValueTo(ratesByDateTo.getValue())
                    .setCounter(1L);
            counterRepository.save(valutesExchangeCounter);
            return;
        }
        counterRepository.increment(counterById.get());
    }

    @Override
    public List<ValuteStatisticDTO> findCounterForLastWeek() {
        Date now = new Date(System.currentTimeMillis());
        LocalDateTime ldt = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).minusDays(7);
        List<ValutesExchangeCounter> counters = counterRepository.findAllByDateValutes_DateAfter(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
        List<ValuteStatisticDTO> stats = new ArrayList<>();
        Valute valuteFrom = null;
        Valute valuteTo = null;
        for (ValutesExchangeCounter counter : counters) {
            valuteFrom = counter.getDateValutes().getValuteFrom();
            valuteTo = counter.getDateValutes().getValuteTo();
            boolean exists = false;
            for (ValuteStatisticDTO stat : stats) {
                if (stat.getValuteFrom().equals(valuteFrom)
                        && stat.getValuteTo().equals(valuteTo)) {
                    stat.setCount(stat.getCount() + counter.getCounter());
                    exists = true;
                }
            }
            if (!exists) {
                stats.add(new ValuteStatisticDTO().setValuteFrom(valuteFrom)
                        .setValuteTo(valuteTo)
                        .setCount(counter.getCounter()));
            }
        }
        return stats;
    }
}
