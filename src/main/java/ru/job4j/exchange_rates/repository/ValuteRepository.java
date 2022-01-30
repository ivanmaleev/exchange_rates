package ru.job4j.exchange_rates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.exchange_rates.model.Valute;

@Repository
public interface ValuteRepository extends JpaRepository<Valute, String> {
}
