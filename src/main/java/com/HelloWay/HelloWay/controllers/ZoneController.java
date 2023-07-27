package com.HelloWay.HelloWay.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.entities.Zone;
import com.HelloWay.HelloWay.services.SpaceService;
import com.HelloWay.HelloWay.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired
    SpaceService spaceService;
    ZoneService zoneService;

    @Autowired
    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Zone addNewZone(@RequestBody Zone zone) {
        return zoneService.addZone(zone);
    }

    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<Zone> allZones(){
        return zoneService.findAllZones();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Zone findZoneById(@PathVariable("id") long id){
        return zoneService.findZoneById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public Zone updateZone(@RequestBody Zone zone){
        return zoneService.updateZone(zone); }

    @PutMapping("/update/{zoneId}")
    @ResponseBody
    public ResponseEntity<?> updateZone(@RequestBody Zone zone, @PathVariable long zoneId){
        Zone exestingZone = zoneService.findZoneById(zoneId);
        Space space = zone.getSpace();
        List<Zone> spaceZones = space.getZones();
        spaceZones.remove(exestingZone);
        for (Zone z : spaceZones){
            if (z.getZoneTitle().equals(exestingZone.getZoneTitle())){
                return ResponseEntity.badRequest().body("zone exist with this title please try with an other");
            }
        }
        return ResponseEntity.ok().body(zoneService.updateZone(zone));
    }

    //TODO ::
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteZone(@PathVariable("id") long id){
        // deleteAllBoardsAttachedWithThisZone
        zoneService.deleteZone(id); }

    @PostMapping("/add/id_space/{id_space}")
    @ResponseBody
    public ResponseEntity<?> addZoneByIdSpace(@RequestBody Zone zone, @PathVariable Long id_space) {
      Space space = spaceService.findSpaceById(id_space);
      Zone zoneObject = new Zone();
      zoneObject = zone;
        List<Zone> zones = new ArrayList<Zone>();
        zones = space.getZones();
      for (Zone zone1 : zones){
          if (zone1.getZoneTitle().equals(zone.getZoneTitle())){
              return ResponseEntity.badRequest().body("zone exist with this title please try with an other ");
          }
      }
      zoneObject.setSpace(space);
      zoneService.addZone(zoneObject);
      zones.add(zoneObject);
      space.setZones(zones);
      spaceService.updateSpace(space);
      return ResponseEntity.ok().body(zoneObject);

    }

    @GetMapping("/all/id_space/{id_space}")
    @ResponseBody
    public List<Zone> getZonesByIdSpace(@PathVariable Long id_space){
        Space space = spaceService.findSpaceById(id_space);
        return space.getZones();
    }

    @GetMapping("/servers/{zoneId}")
    @ResponseBody
    public ResponseEntity<?> getServersByIdZone(@PathVariable long zoneId){
        Zone zone = zoneService.findZoneById(zoneId);
        if (zone == null){
            return ResponseEntity.badRequest().body("zone doesn't exist with zone id : " + zoneId);
        }
        return ResponseEntity.ok().body(zoneService.getServersByZone(zone));
    }

}
