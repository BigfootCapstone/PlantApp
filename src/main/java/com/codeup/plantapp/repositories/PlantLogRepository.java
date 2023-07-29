package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.Plant;
import com.codeup.plantapp.models.PlantLog;
import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantLogRepository extends JpaRepository<PlantLog, Long> {
//    PlantLog findPlantLogById(long id);
    List<PlantLog> findTop5ByUserOrderByCreatedAtDesc(User user);

    List<PlantLog> findPlantLogByGardenPlant(GardenPlant gardenPlant);
}