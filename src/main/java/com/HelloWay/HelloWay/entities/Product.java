package com.HelloWay.HelloWay.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(	name = "products")
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @NotBlank
    @Column(length = 50)
    private String productTitle;


    @Column(length = 20)
    private Float price;

    @NotBlank
    @Column(length = 100)
    private String description;



    @OneToMany
    List<CommandProduct> commandProducts;

    @OneToMany
    List<BasketProduct> basketProducts;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @OneToMany(mappedBy="product")
    private List<Image> images;


}
