package ru.job4j.exchange_rates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.exchange_rates.model.RatesByDate;
import ru.job4j.exchange_rates.model.Valute;

import java.util.Date;
import java.util.Optional;

public interface RatesByDateRepository extends JpaRepository<RatesByDate, RatesByDate.DateValute> {
    Optional<RatesByDate> findDistinctFirstByDateValute_ValuteAndDateValute_DateBefore(Valute valute, Date date);
}
