package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GardenPlantRepository extends JpaRepository<GardenPlant, Long> {
    GardenPlant findGardenPlantsById(long id);

    List<GardenPlant> findGardenPlantByUser(User user);
}