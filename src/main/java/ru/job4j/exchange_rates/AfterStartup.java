package ru.job4j.exchange_rates;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.job4j.exchange_rates.service.ValuteService;

import javax.xml.bind.JAXBException;
import java.util.Date;

@Component
@AllArgsConstructor
public class AfterStartup {

    private ValuteService valuteService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws JAXBException {
        valuteService.loadValutes();
        Date date = new Date(System.currentTimeMillis());
        valuteService.loadRatesByDate(date);
    }
}