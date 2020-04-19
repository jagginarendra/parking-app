package com.parking.app.repository;

import com.parking.app.model.jpa.FloorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<FloorDTO, Integer> {
}

