package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Categorie;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.entities.SpaceCategorie;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private    UserService userService;

    @Autowired
    private    CategorieService categorieService;

    @Autowired
    ImageService imageService;







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





}
