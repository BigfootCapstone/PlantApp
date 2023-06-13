package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findPlantById(long id);
}