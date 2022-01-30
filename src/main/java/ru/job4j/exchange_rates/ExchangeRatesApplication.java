package ru.job4j.exchange_rates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.client.RestTemplate;
import ru.job4j.exchange_rates.service.ValuteService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class ExchangeRatesApplication extends SpringBootServletInitializer {

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DateFormatter getDateFormatter() {
        return new DateFormatter();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExchangeRatesApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRatesApplication.class, args);
    }
}
