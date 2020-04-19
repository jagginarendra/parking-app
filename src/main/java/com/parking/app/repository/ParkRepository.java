package com.parking.app.repository;

import com.parking.app.model.jpa.ParkDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends JpaRepository<ParkDTO, Integer> {

}
