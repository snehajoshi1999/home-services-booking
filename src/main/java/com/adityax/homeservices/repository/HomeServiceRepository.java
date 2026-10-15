package com.adityax.homeservices.repository;

import com.adityax.homeservices.entity.HomeService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeServiceRepository extends JpaRepository<HomeService, Long> {

    List<HomeService> findByNameContainingIgnoreCase(String name);
}
