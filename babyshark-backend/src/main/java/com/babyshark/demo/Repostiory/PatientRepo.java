package com.babyshark.demo.Repostiory;

import com.babyshark.demo.DTO.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends CrudRepository<Patient, String> {

}
