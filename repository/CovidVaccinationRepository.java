package com.lab.api.covidvaccination.repository;

import com.lab.api.covidvaccination.model.CovidVaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CovidVaccinationRepository extends JpaRepository<CovidVaccination, Integer> {
}
