package com.babyshark.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Patients")
public class Patient {
    @Getter
    @Setter
    @Id
    private String NRIC;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int age = 10;

}
