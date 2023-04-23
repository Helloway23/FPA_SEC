package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Board;
import com.HelloWay.HelloWay.entities.Categorie;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.entities.Zone;
import com.HelloWay.HelloWay.repos.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {
    @Autowired
    CategorieRepository categorieRepository ;
    @Autowired
    SpaceService spaceService;
    @Autowired
    BoardService boardService;

    public Optional<Categorie> addCategorie(Categorie categorie) throws Exception{
        if (!categorieRepository.existsByCategoryTitle(categorie.getCategoryTitle()))
        return Optional.of(categorieRepository.save(categorie));
        else throw new IllegalArgumentException("categorie exists");

    }
    public List<Categorie> findAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie updateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie findCategorieById(Long id) {
        return categorieRepository.findById(id)
                .orElse(null);
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    public Categorie addCategorieByIdSpace(Categorie categorie,Long idSpace){

        Space space = spaceService.findSpaceById(idSpace);
        Categorie categorieObject = new Categorie();
        categorieObject = categorie;
        List<Categorie> categories = new ArrayList<Categorie>();
        categorieObject.setSpace(space);
        categorieRepository.save(categorieObject);
        categories = space.getCategories();
        categories.add(categorieObject);
        space.setCategories(categories);
        spaceService.updateSpace(space);


        return categorieObject ;
    }
    public List<Categorie> getCategoriesByIdSpace(Long idSpace){

        Space space = spaceService.findSpaceById(idSpace);
        return space.getCategories();
    }

    public List<Categorie> getCategoriesByQrCodeTable(String qrCode){

       Board board = boardService.getBoardByQrCode(qrCode);
        Zone zone = board.getZone();
        return zone.getSpace().getCategories();
    }


}
