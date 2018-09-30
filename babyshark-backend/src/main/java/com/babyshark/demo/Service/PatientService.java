package com.babyshark.demo.Service;

import com.babyshark.demo.DTO.Appointment;
import com.babyshark.demo.DTO.Patient;
import com.babyshark.demo.Repostiory.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    public List<Patient> findAll() {
        return (List<Patient>) patientRepo.findAll();
    }

    public void addPatient(Patient patient) {
        patientRepo.save(patient);
    }

    public Patient getPatientById(String nric) {
        return patientRepo.findById(nric).get();
    }

    public void deletePatient(Patient patient) {
        patientRepo.delete(patient);
    }

    public long numOfPatient() {
        return patientRepo.count();
    }

    public void updatePatient(Patient patient) {
        patientRepo.save(patient);
    }

    public List<Appointment> getPatientAppointments(String nric) {
        return getPatientById(nric).getAppointments();
    }

}
