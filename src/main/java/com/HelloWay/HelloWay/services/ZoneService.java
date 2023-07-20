package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.User;
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

    public Zone updateZone(Zone updatedZone) {
        Zone existingZone = zoneRepository.findById(updatedZone.getIdZone()).orElse(null);
        if (existingZone != null) {
            // Copy the properties from the updatedZone to the existingZone
            existingZone.setZoneTitle(updatedZone.getZoneTitle());
            zoneRepository.save(existingZone);
            return existingZone;
        } else {
            // Handle the case where the zone doesn't exist in the database
            // You may throw an exception or handle it based on your use case.
            return null;
        }
    }
    public Zone findZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElse(null);
    }

    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }

    public List<User> getServersByZone(Zone zone){
        return  zone.getServers();
    }


}
