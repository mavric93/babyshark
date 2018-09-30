package com.babyshark.demo.Controller;

import com.babyshark.demo.DTO.Patient;
import com.babyshark.demo.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public List<Patient> getPatients() {
        Patient patient = new Patient();
        patient.setAge(20);
        patient.setName("John");
        patient.setNRIC("S9312345k");
        patientService.addPatient(patient);
        return patientService.findAll();
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public void addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @RequestMapping(value = "/patient", method = RequestMethod.PUT)
    public void bulkAddPatients(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @RequestMapping(value = "/patient", method = RequestMethod.DELETE)
    public void deletePatients() {
        // dangerous method not implemented
    }

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.GET)
    public void getPatient(@PathVariable("id") String nric) {
        patientService.getPatientById(nric);
    }

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.PUT)
    public void updatePatient(@PathVariable("id") String nric, @RequestBody Patient patient) {
        patientService.updatePatient(patient);
    }
}