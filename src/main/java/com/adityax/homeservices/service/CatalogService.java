package com.adityax.homeservices.service;

import com.adityax.homeservices.dto.ServiceResponse;
import com.adityax.homeservices.entity.HomeService;
import com.adityax.homeservices.exception.ResourceNotFoundException;
import com.adityax.homeservices.repository.HomeServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final HomeServiceRepository homeServiceRepository;

    public CatalogService(HomeServiceRepository homeServiceRepository) {
        this.homeServiceRepository = homeServiceRepository;
    }

    public List<ServiceResponse> getServices(String search) {
        List<HomeService> services;
        if (search == null || search.isBlank()) {
            services = homeServiceRepository.findAll();
        } else {
            services = homeServiceRepository.findByNameContainingIgnoreCase(search.trim());
        }
        return services.stream().map(ServiceResponse::from).toList();
    }

    public ServiceResponse getServiceById(Long id) {
        HomeService service = homeServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + id));
        return ServiceResponse.from(service);
    }
}
