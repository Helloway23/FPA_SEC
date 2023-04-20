package com.HelloWay.HelloWay.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(	name = "reservations")
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation ;

    @NotBlank
    @Column(length = 20)
    private LocalDate bookingDate ;

    @NotBlank
    @Size(max = 20)
    private int numberOfGuests ;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_space")
    private Space space ;

    @OneToMany(mappedBy = "reservation")
    List<Board> boards ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "reservation_products",
            joinColumns = @JoinColumn(name = "id_reservation"),
            inverseJoinColumns = @JoinColumn(name = "id_product"))
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idEventTitle")
    private EventTitle eventTitle;


}
