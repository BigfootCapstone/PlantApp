package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.PlantLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantLogRepository extends JpaRepository<PlantLog, Long> {

    PlantLog findById(long id);
}