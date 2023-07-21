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


    //TODO ::
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteZone(@PathVariable("id") long id){
        // deleteAllBoardsAttachedWithThisZone
        zoneService.deleteZone(id); }

    @PostMapping("/add/id_space/{id_space}")
    @ResponseBody
    public Zone addZoneByIdSpace(@RequestBody Zone zone, @PathVariable Long id_space) {
      Space space = spaceService.findSpaceById(id_space);
      Zone zoneObject = new Zone();
      zoneObject = zone;
      zoneObject.setSpace(space);
      zoneService.addZone(zoneObject);
      List<Zone> zones = new ArrayList<Zone>();
      zones = space.getZones();
      zones.add(zoneObject);
      space.setZones(zones);
      spaceService.updateSpace(space);
      return zoneObject;

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
