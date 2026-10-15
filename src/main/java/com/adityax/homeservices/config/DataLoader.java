package com.adityax.homeservices.config;

import com.adityax.homeservices.entity.HomeService;
import com.adityax.homeservices.repository.HomeServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Inserts the starting list of services on startup.
 * Runs only when the table is empty, so restarting the app does not create duplicates.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final HomeServiceRepository homeServiceRepository;

    public DataLoader(HomeServiceRepository homeServiceRepository) {
        this.homeServiceRepository = homeServiceRepository;
    }

    @Override
    public void run(String... args) {
        if (homeServiceRepository.count() > 0) {
            return;
        }

        homeServiceRepository.saveAll(List.of(
                new HomeService("Home Deep Cleaning",
                        "Full house cleaning including floors, windows, furniture and dusting",
                        new BigDecimal("1499.00"), 4.6),
                new HomeService("Bathroom Cleaning",
                        "Deep cleaning of tiles, taps, toilet and wash basin",
                        new BigDecimal("399.00"), 4.5),
                new HomeService("Kitchen Cleaning",
                        "Degreasing of chimney, stove, slab, sink and cabinets",
                        new BigDecimal("699.00"), 4.4),
                new HomeService("Plumbing",
                        "Leak repair, tap and pipe fitting, blockage removal",
                        new BigDecimal("199.00"), 4.3),
                new HomeService("Electrician",
                        "Switch, socket, fan and light installation and repair",
                        new BigDecimal("149.00"), 4.5),
                new HomeService("AC Service and Repair",
                        "AC servicing, gas refill and repair for split and window ACs",
                        new BigDecimal("499.00"), 4.2),
                new HomeService("Carpentry",
                        "Furniture repair, door and lock fitting, minor woodwork",
                        new BigDecimal("249.00"), 4.1),
                new HomeService("Painting",
                        "Interior and exterior wall painting with free colour consultation",
                        new BigDecimal("2999.00"), 4.4),
                new HomeService("Pest Control",
                        "Treatment for cockroaches, ants, termites and mosquitoes",
                        new BigDecimal("899.00"), 4.3),
                new HomeService("Appliance Repair",
                        "Repair of washing machine, refrigerator and microwave",
                        new BigDecimal("299.00"), 4.0)
        ));
    }
}
