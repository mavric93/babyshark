package com.babyshark.demo.Controller;

import com.babyshark.demo.DTO.Patient;
import com.babyshark.demo.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping("/patient")
    public List<Patient> getPatients() {
        return patientService.findAll();
    }

}