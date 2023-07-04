package com.HelloWay.HelloWay.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("party")
public class Party extends Event {
    private int nbParticipant ;
    private double price ;

    private Boolean allInclusive ;

    public Party(int nbParticipant, double price, Boolean allInclusive) {
        this.nbParticipant = nbParticipant;
        this.price = price;
        this.allInclusive = allInclusive;
    }

    public Party() {
    }

    public int getNbParticipant() {
        return nbParticipant;
    }

    public void setNbParticipant(int nbParticipant) {
        this.nbParticipant = nbParticipant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getAllInclusive() {
        return allInclusive;
    }

    public void setAllInclusive(Boolean allInclusive) {
        this.allInclusive = allInclusive;
    }
}
