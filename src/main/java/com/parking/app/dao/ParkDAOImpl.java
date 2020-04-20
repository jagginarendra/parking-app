package com.parking.app.dao;

import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.repository.ParkRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ParkDAOImpl  implements ParkDAO{

    @Autowired
    ParkRepository parkRepository;

    @Override
    public void saveAll(List<ParkDTO> parkings) {
        parkRepository.saveAll(parkings);
    }

    @Override
    public void save(ParkDTO parking) {
        parkRepository.save(parking);
    }
}
