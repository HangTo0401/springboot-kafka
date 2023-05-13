package fr.alex.config;

import fr.alex.init.CountryInit;
import fr.alex.init.GenderInit;
import fr.alex.repository.CountryRepository;
import fr.alex.repository.GenderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfig {

    @Bean
    public GenderInit genderInit(GenderRepository genderRepository) {
        return new GenderInit(genderRepository);
    }

    @Bean
    public CountryInit countryInit(CountryRepository countryRepository) {
        return new CountryInit(countryRepository);
    }

    @Bean
    public CommandLineRunner initDb(GenderInit genderInit, CountryInit countryInit) {
        return arg -> {

            genderInit.init();
            countryInit.init();

        };
    }}
