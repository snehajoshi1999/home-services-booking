package com.adityax.homeservices.controller;

import com.adityax.homeservices.dto.ServiceResponse;
import com.adityax.homeservices.service.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class HomeServiceController {

    private final CatalogService catalogService;

    public HomeServiceController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<ServiceResponse> getServices(@RequestParam(name = "search", required = false) String search) {
        return catalogService.getServices(search);
    }

    @GetMapping("/{id}")
    public ServiceResponse getServiceById(@PathVariable("id") Long id) {
        return catalogService.getServiceById(id);
    }
}
