package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.GardenPlant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenPlantRepository extends JpaRepository<GardenPlant, Long> {

    GardenPlant findById(long id);

}