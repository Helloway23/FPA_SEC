package com.HelloWay.HelloWay.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ToString
@Getter
@Setter
@Entity
@Table(name = "eventtitels")
@NoArgsConstructor
@AllArgsConstructor
public class EventTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEventTitle;

    @NotBlank
    @Column(length = 20)
    private String title;

    @OneToMany(mappedBy="eventTitle")
    private List<Reservation> reservations;
}
