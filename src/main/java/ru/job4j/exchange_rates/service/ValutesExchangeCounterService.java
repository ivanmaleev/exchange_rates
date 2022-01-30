package ru.job4j.exchange_rates.service;

import ru.job4j.exchange_rates.model.RatesByDate;
import ru.job4j.exchange_rates.model.ValuteStatisticDTO;

import java.util.List;

public interface ValutesExchangeCounterService {

    void incrementCounter(RatesByDate ratesByDateFrom, RatesByDate ratesByDateTo);

    List<ValuteStatisticDTO> findCounterForLastWeek();
}
