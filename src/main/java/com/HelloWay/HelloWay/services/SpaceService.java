package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.*;
import com.HelloWay.HelloWay.exception.ResourceNotFoundException;
import com.HelloWay.HelloWay.repos.SpaceRepository;
import com.HelloWay.HelloWay.repos.ZoneRepository;
import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private    UserService userService;

    @Autowired
    private    CategorieService categorieService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private  ZoneRepository zoneRepository;


    public List<Space> findAllSpaces() {
        return spaceRepository.findAll();
    }

    public Space updateSpace(Space space) {
        return spaceRepository.save(space);
    }

    public Space addNewSpace(Space space) throws IOException {

        return spaceRepository.save(space);
    }

    public Space findSpaceById(Long id) {
        return spaceRepository.findById(id)
                .orElse(null);
    }

    public void deleteSpace(Long id) {
        spaceRepository.deleteById(id);
    }

    public  Space addSpaceByIdModeratorAndIdSpaceCategory(Space space, Long idG, Long idSpaceCategorie){

        if (idSpaceCategorie==1){space.setSpaceCategorie(SpaceCategorie.Restaurant);}
        if (idSpaceCategorie==2){space.setSpaceCategorie(SpaceCategorie.Cafes);}
        if (idSpaceCategorie==3){space.setSpaceCategorie(SpaceCategorie.Bar);}

        Space spaceObject= new Space();
        spaceObject = space ;
        User user = userService.findUserById(idG);
        spaceObject.setModerator(user);

        spaceRepository.save(spaceObject);

        user.setModeratorSpace(spaceObject);
        userService.updateUser(user);

        return spaceObject;


    }

    public  Space addSpaceByIdModeratorAndSpaceCategory(Space space, Long idG,Long idSpaceCategorie){


        Space spaceObject= new Space();
        spaceObject = space ;
        if (idSpaceCategorie == 1){spaceObject.setSpaceCategorie(SpaceCategorie.Restaurant);}
        if (idSpaceCategorie == 2){spaceObject.setSpaceCategorie(SpaceCategorie.Bar);}
        if (idSpaceCategorie == 3){spaceObject.setSpaceCategorie(SpaceCategorie.Cafes);}

        User user = userService.findUserById(idG);

        spaceObject.setModerator(user);

        spaceRepository.save(spaceObject);

        user.setModeratorSpace(spaceObject);
        userService.updateUser(user);

        return spaceObject;


    }

    public Space getSpaceByIdModerator(Long idModerator){

        User user = userService.findUserById(idModerator);
        return  user.getModeratorSpace();
    }

    public Space getSpaceByIdCategory(Long idCategory){

        Categorie categorie = categorieService.findCategorieById(idCategory);
        return  categorie.getSpace();
    }

    public List<Space> getSpacesByIdSpaceCategory(Long idCategory){

        List<Space> spaces = new ArrayList<Space>();
        List<Space> resSpaces = new ArrayList<Space>();
        spaces = spaceRepository.findAll();
        for (Space space:spaces){
            if (space.getSpaceCategorie().ordinal() == idCategory){
                resSpaces.add(space);
            }
        }

        return  resSpaces;
    }


    public void setServerInZone(Long spaceId, Long moderatorUserId, Long serverId, Long zoneId) throws NotFoundException {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found"));

        User moderator = userService.findUserById(moderatorUserId);


        // Check if the user is the moderator of the space
        if (!space.getModerator().equals(moderator)) {
            throw new ResourceNotFoundException("User is not the moderator of the space");
        }

        User server = userService.findUserById(serverId);

        // Check if the user is the moderator of the space
        if (!space.getServers().contains(server)) {
            throw new ResourceNotFoundException("User is not a server in  the space");
        }

        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        // Update the server's zone
        server.setZone(zone);
        userService.addUser(server);
    }

    public void addServerInSpace(Long spaceId, Long moderatorUserId, Long serverId) throws NotFoundException {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found"));

        User moderator = userService.findUserById(moderatorUserId);


        // Check if the user is the moderator of the space
        if (!space.getModerator().equals(moderator)) {
            throw new ResourceNotFoundException("User is not the moderator of the space");
        }

        User server = userService.findUserById(serverId);

        // Update the server's space
        server.setServersSpace(space);
       // userService.addUser(server);
        List<User> spaceServers = new ArrayList<>();
        spaceServers = space.getServers();
        spaceServers.add(server);
        space.setServers(spaceServers);
        spaceRepository.save(space);
    }

    public Page<Space> getSpaces(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return spaceRepository.findAll(pageable);
    }

    public void deleteServerFromZone(Long spaceId, Long moderatorUserId, Long serverId, Long zoneId) throws NotFoundException {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found"));

        User moderator = userService.findUserById(moderatorUserId);


        // Check if the user is the moderator of the space
        if (!space.getModerator().equals(moderator)) {
            throw new ResourceNotFoundException("User is not the moderator of the space");
        }

        User server = userService.findUserById(serverId);

        // Check if the user is the moderator of the space
        if (!space.getServers().contains(server)) {
            throw new ResourceNotFoundException("User is not a server in  the space");
        }

        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        // Update the server's zone
        server.setZone(null);
        userService.addUser(server);
    }

    public List<User> getServersBySpace(Space space){
        return space.getServers();
    }
    public Space getSpaceByWaiterId(User waiter){return waiter.getServersSpace();}

    public Space addNewRate(Space space, Float newRate){

        long numberOfRate = space.getNumberOfRate() + 1;
        float rate = space.getRating();

        float totalRate = rate + newRate ;
        float result = totalRate / numberOfRate;
        space.setNumberOfRate(numberOfRate);
        space.setRating(result);
        return  spaceRepository.save(space);
    }


}
