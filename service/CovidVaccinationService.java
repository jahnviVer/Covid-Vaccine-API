package com.lab.api.covidvaccination.service;

import com.lab.api.covidvaccination.model.CovidVaccination;
import com.lab.api.covidvaccination.repository.CovidVaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CovidVaccinationService {

    @Autowired
    private CovidVaccinationRepository covidVaccinationRepository;

    private static final String BASE_URI = "http://localhost:8080/covid-vaccination/";
    private static final String GET_BY_ID_API = "fetchVaccinatedData/";
    private static final String SERVICE_INVOKE_HTTP_ERROR = "Unexpected error occurred while invoking the service.";
    private static final String VERIFICATION_ID_NEGATIVE = "Verification id can not be zero or less than zero.";
    private static final String DELETE_ID_SUCCESS_MSG = "The Beneficiary details of the provided id is deleted successfully.";
    private static final String BENEFICIARY_NAME_EMPTY_ERROR = "Beneficiary name can not be null or empty.";
    private static final String VERIFICATION_ID_NOT_VALID = "Verification id is not valid.";


    /**
     * Stores the Details for the Vaccinated beneficiaries
     */
    public CovidVaccination addBeneficiaryDetails(CovidVaccination covidVaccination) {
        if (StringUtils.isEmpty(covidVaccination.getBeneficiaryName())) {
            throw new ValidationException(BENEFICIARY_NAME_EMPTY_ERROR);
        }
        return covidVaccinationRepository.save(covidVaccination);

    }

    /**     * Fetches the Details of the Vaccinated beneficiaries provided the unique Id
     */
    public CovidVaccination fetchBeneficiaryDetailById(int verificationId) {
        //Verification Id Can not be 0 or negative
        if (Optional.of(verificationId).orElse(0) <= 0) {
            throw new ValidationException(VERIFICATION_ID_NEGATIVE);
        }
        CovidVaccination covidVaccination = covidVaccinationRepository.findOne(verificationId);
        //If Verification Id not present in H2 database
        if (Objects.isNull(covidVaccination)) {
            throw new ValidationException(VERIFICATION_ID_NOT_VALID);
        }
        return covidVaccination;
    }

    /**
     * Fetches the Details of all the Vaccinated beneficiaries
     */
    public List<CovidVaccination> fetchAllBeneficiaryDetails() {
        return covidVaccinationRepository.findAll();
    }

    /**
     * Updates the Details of all the Vaccinated beneficiaries
     */
    public CovidVaccination updateBeneficiaryDetailById(CovidVaccination covidVaccinationRequest) {
        RestTemplate restTemplate = new RestTemplate();
        CovidVaccination covidVaccination;
        //Using getForObject method to fetch the beneficiary details provided the id
        String uri = BASE_URI + GET_BY_ID_API + covidVaccinationRequest.getVerificationId();
        //To handle exception while invoking the GET API
        try {
            covidVaccination = restTemplate.getForObject(uri, CovidVaccination.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, SERVICE_INVOKE_HTTP_ERROR);
        }
        //If Verification Id not present in H2 database
        if (Objects.isNull(covidVaccination)) {
            throw new ValidationException(VERIFICATION_ID_NOT_VALID);
        }
        //Update the details
        covidVaccination.setBeneficiaryName(covidVaccinationRequest.getBeneficiaryName());
        //Post the details
        return covidVaccinationRepository.save(covidVaccination);
    }

    /**
     * Deletes the Details of the Vaccinated beneficiaries provided the unique Id
     */
    public String deleteBeneficiaryDetailById(int verificationId) {
        if (Optional.of(verificationId).orElse(0) <= 0) {
            throw new ValidationException(VERIFICATION_ID_NEGATIVE);
        }
        covidVaccinationRepository.delete(verificationId);
        return DELETE_ID_SUCCESS_MSG;
    }

}

