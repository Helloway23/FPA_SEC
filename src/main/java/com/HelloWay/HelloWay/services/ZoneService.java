package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Zone;
import com.HelloWay.HelloWay.repos.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;




    public Zone addZone(Zone zone){
        return zoneRepository.save(zone);
    }
    public List<Zone> findAllZones() {
        return zoneRepository.findAll();
    }

    public Zone updateZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    public Zone findZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElse(null);
    }

    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }


}
