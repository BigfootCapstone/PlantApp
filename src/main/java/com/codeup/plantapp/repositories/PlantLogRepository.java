package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.Plant;
import com.codeup.plantapp.models.PlantLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantLogRepository extends JpaRepository<PlantLog, Long> {
    PlantLog findPlantLogById(long id);

    List<PlantLog> findPlantLogByGardenPlant(GardenPlant gardenPlant);
}