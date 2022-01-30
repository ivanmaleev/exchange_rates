package ru.job4j.exchange_rates.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.exchange_rates.model.ValuteStatisticDTO;
import ru.job4j.exchange_rates.service.ValutesExchangeCounterService;

import java.util.List;

//контроллер статистики
@Controller
@AllArgsConstructor
public class StatisticsController {

    private ValutesExchangeCounterService exchangeCounterService;

    @GetMapping("/statistics")
    public String openAllStatistics(Model model) {
        List<ValuteStatisticDTO> counterForLastWeek = exchangeCounterService.findCounterForLastWeek();
        model.addAttribute("counters", counterForLastWeek);
        return "statistics";
    }
}
