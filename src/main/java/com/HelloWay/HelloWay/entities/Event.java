package com.HelloWay.HelloWay.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@ToString
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;

    @NotBlank
    @Column(length = 20)
    private String eventTitle;

    @NotBlank
    @Column(length = 20)
    private LocalDate startDate ;

    @NotBlank
    @Column(length = 20)
    private LocalDate endDate ;

    @NotBlank
    @Column(length = 40)
    private String description;

    @NotBlank
    @Column(length = 40)
    private double price ;

    @NotBlank
    @Column(length = 40)
    private int nbParicpants ;

    @NotBlank
    @Column(length = 40)
    private Boolean promotion ;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idSpace")
    private Space space;





}
