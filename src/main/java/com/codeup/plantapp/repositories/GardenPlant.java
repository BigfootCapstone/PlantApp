package com.codeup.plantapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenPlant extends JpaRepository<GardenPlant, Long> {

        GardenPlant findById(long id);
}