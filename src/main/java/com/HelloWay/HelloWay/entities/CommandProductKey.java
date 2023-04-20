package com.HelloWay.HelloWay.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandProductKey implements Serializable {
    @Column(name = "idCommand")
    private Long groupId ;
    @Column(name = "idProduct")
    private Long subjectId;
}
