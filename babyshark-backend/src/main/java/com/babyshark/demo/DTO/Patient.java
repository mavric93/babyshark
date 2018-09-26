package com.babyshark.demo.DTO;

import lombok.Getter;
import lombok.Setter;

public class Patient {
    @Getter
    @Setter
    private String NRIC;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int age = 10;
    
}
