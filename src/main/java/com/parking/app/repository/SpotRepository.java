package com.parking.app.repository;

import com.parking.app.model.jpa.ParkingSpotDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<ParkingSpotDTO, Integer> {

    @Query("select s from ParkingSpotDTO s where s.companyId = :parkingLotId and s.occupancyStatus in :occupancy_status")
    List<ParkingSpotDTO> findByParkingLotIdAndOccupancyStatus(@Param("parkingLotId") int parkingLotID, @Param("occupancy_status") List<String> occupancy_status );

    List<ParkingSpotDTO> findByCompanyId(int companyId);

}
