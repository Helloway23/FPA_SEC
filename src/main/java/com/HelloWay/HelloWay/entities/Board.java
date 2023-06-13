package com.HelloWay.HelloWay.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTable ;

    //Unique
    @Positive
    private int numTable ;


    @Column(length = 20)
    private boolean availability ;


    //Unique
    @NotBlank
    @Column(length = 50)
    private String qrCode;

    @OneToOne(mappedBy = "board")
    Basket basket ;

    @JsonIgnore
    @ManyToOne
    Zone zone ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id")
    private User user;


    @OneToMany(mappedBy = "board")
    List<Command> commands ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_reservation")
    private Reservation reservation ;


}
