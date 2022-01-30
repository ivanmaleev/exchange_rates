package ru.job4j.exchange_rates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.exchange_rates.model.Valute;
import ru.job4j.exchange_rates.service.ValuteService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//контроллер главной страницы
@Controller
public class IndexController {

    private ValuteService valuteService;
    private Map<String, Valute> valutes;

    public IndexController(ValuteService valuteService) {
        this.valuteService = valuteService;
        this.valutes = valuteService.findAllValutesToMap();
    }

    //Get метод главной страницы
    @GetMapping({"/", "index"})
    public String index(Model model, HttpServletRequest req) {

        final String valuteFromId = req.getParameter("valuteFrom.id");
        final String valuteToId = req.getParameter("valuteTo.id");
        final String countFrom = req.getParameter("countFrom");

        if (valuteFromId != null
                || valuteToId != null
                || countFrom != null) {
            double result = valuteService.calculateExchage(valuteFromId, valuteToId, countFrom);
            model.addAttribute("result", result);
            model.addAttribute("valuteFromId", valuteFromId);
            model.addAttribute("valuteToId", valuteToId);
            model.addAttribute("countFrom", countFrom);
        } else {
            model.addAttribute("result", 0);
            model.addAttribute("countFrom", 0);
        }
        if (valutes.size() == 0) {
            this.valutes = valuteService.findAllValutesToMap();
        }
        List<Valute> valutesList = valutes.entrySet().stream()
                .map(Map.Entry::getValue).collect(Collectors.toList());
        model.addAttribute("valutes", valutesList);
        return "index";
    }
}
