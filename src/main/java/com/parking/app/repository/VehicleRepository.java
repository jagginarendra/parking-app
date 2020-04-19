package com.parking.app.repository;

import com.parking.app.model.jpa.VehicleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleDTO, Integer> {

    Optional<VehicleDTO> getByRegistrationNumber(String registrationNumber);
}
