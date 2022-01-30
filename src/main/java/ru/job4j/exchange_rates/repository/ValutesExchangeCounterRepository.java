package ru.job4j.exchange_rates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.exchange_rates.model.ValutesExchangeCounter;

import java.util.Date;
import java.util.List;

public interface ValutesExchangeCounterRepository extends
        JpaRepository<ValutesExchangeCounter, ValutesExchangeCounter.DateValutes> {

    List<ValutesExchangeCounter> findAllByDateValutes_DateAfter(Date date);

    @Modifying
    @Query("update ValutesExchangeCounter vec set vec.counter = (vec.counter + 1) where vec = :vec")
    void increment(ValutesExchangeCounter vec);
}
