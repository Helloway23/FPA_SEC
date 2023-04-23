package com.HelloWay.HelloWay.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.HelloWay.HelloWay.entities.Categorie;
import com.HelloWay.HelloWay.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    CategorieService categorieService;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Optional<Categorie> addNewCategorie(@RequestBody Categorie categorie) throws Exception {
        return categorieService.addCategorie(categorie);
    }

    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<Categorie> allCategories(){
        return categorieService.findAllCategories();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Categorie findCategorieById(@PathVariable("id") long id){
        return categorieService.findCategorieById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateCategorie(@RequestBody Categorie categorie){
        categorieService.updateCategorie(categorie); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteCategorie(@PathVariable("id") long id){
        //delete all products attached with this categorie
        categorieService.deleteCategorie(id); }

    @PostMapping("/add/id_space/{id_space}")
    @ResponseBody
    public Categorie addNewCategorieByIdSpace(@RequestBody Categorie categorie, @PathVariable Long id_space) {
        return categorieService.addCategorieByIdSpace(categorie, id_space);
    }

    @GetMapping("/id_space/{id_space}")
    @ResponseBody
    public List<Categorie> getCategoriesByIdSpace( @PathVariable Long id_space) {
        return categorieService.getCategoriesByIdSpace( id_space);
    }

    @GetMapping("/qr_code/{qr_code}")
    @ResponseBody
    public List<Categorie> getCategoriesByQrCodeTable( @PathVariable String qr_code) {
        return categorieService.getCategoriesByQrCodeTable(qr_code);
    }





}
