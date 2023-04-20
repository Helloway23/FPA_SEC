package com.HelloWay.HelloWay.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(	name = "command_product")
@NoArgsConstructor
public class CommandProduct implements Serializable {
    @EmbeddedId
    private CommandProductKey id;

    @ManyToOne
    @MapsId("idCommand")
    @JoinColumn(name = "idCommand")
    private Command command;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "idProduct")
    private Product product;

    private int quantityOrdered;
}
