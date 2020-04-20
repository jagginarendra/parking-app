package com.parking.app.dao;

import com.parking.app.model.jpa.ParkDTO;

import java.util.List;

public interface ParkDAO {

    void saveAll(List<ParkDTO> parkings);

    void save(ParkDTO parking);
}
