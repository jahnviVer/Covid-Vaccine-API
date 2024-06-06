package com.lab.api.covidvaccination.controller;

import com.lab.api.covidvaccination.model.CovidVaccination;
import com.lab.api.covidvaccination.service.CovidVaccinationService;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/covid-vaccination")
@Api("Covid Vaccination Details EndPoint")
public class CovidVaccinationController {

    @Autowired
    private CovidVaccinationService covidVaccinationService;
    private static final Logger logger = LoggerFactory.getLogger(CovidVaccinationController.class);

    @PostMapping("/storeVaccinatedData")
    @ApiOperation(
            value = "To add the vaccinated beneficiary details and returns the details as a response. ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Response with Message returned successfully",
                            response = CovidVaccination.class),
                    @ApiResponse(code = 401, message = "User is not authenticated"),
            })
    public CovidVaccination addBeneficiaryDetails(
            @ApiParam(value = "covidVaccination", required = true)
            @RequestBody CovidVaccination covidVaccination) {
        return covidVaccinationService.addBeneficiaryDetails(covidVaccination);
    }

    @ApiOperation(value = "To fetch the beneficiary details based on verification-id. ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "The Beneficiary details is returned successfully",
                            response = CovidVaccination.class),
                    @ApiResponse(
                            code = 400,
                            message = "Bad Request response, if verification id not found in the database"),
                    @ApiResponse(code = 401, message = "User is not authenticated"),
                    @ApiResponse(code = 403, message = "User does not have access to this endpoint"),
            })
    @GetMapping("/fetchVaccinatedData/{verificationId}")
    public CovidVaccination fetchBeneficiaryDetailById(
            @ApiParam(value = "verificationId", required = true)
            @PathVariable int verificationId) {
        return covidVaccinationService.fetchBeneficiaryDetailById(verificationId);
    }


    @GetMapping("/fetchVaccinatedData/fetchAll")
    @ApiOperation(value = "To fetch all the beneficiary details. ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "The Beneficiary details are returned successfully",
                            response = CovidVaccination.class),
                    @ApiResponse(
                            code = 400,
                            message = "Bad Request response, if verification id not found in the database"),
                    @ApiResponse(code = 401, message = "User is not authenticated"),
                    @ApiResponse(code = 403, message = "User does not have access to this endpoint"),
            })
    public List<CovidVaccination> fetchAllBeneficiaryDetails() {
        return covidVaccinationService.fetchAllBeneficiaryDetails();
    }

    @PutMapping("/updateBeneficiaryDetail")
    @ApiOperation(
            value = "To update the vaccinated beneficiary details and returns the details as a response. ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Response with details returned successfully",
                            response = CovidVaccination.class),
                    @ApiResponse(code = 401, message = "User is not authenticated"),
            })
    public CovidVaccination updateBeneficiaryDetailById(
            @ApiParam(value = "covidVaccination", required = true)
            @RequestBody CovidVaccination covidVaccination) {
        return covidVaccinationService.updateBeneficiaryDetailById(covidVaccination);

    }

    @ApiOperation(value = "To delete the beneficiary details based on verification-id. ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "The Beneficiary details is deleted successfully",
                            response = String.class),
                    @ApiResponse(
                            code = 400,
                            message = "Bad Request response, if verification id not found in the database"),
                    @ApiResponse(code = 401, message = "User is not authenticated"),
                    @ApiResponse(code = 403, message = "User does not have access to this endpoint"),
            })
    @DeleteMapping("/deleteVaccinatedData/{verificationId}")
    public String deleteBeneficiaryDetailById(
            @ApiParam(value = "verificationId", required = true)
            @PathVariable int verificationId) {
        return covidVaccinationService.deleteBeneficiaryDetailById(verificationId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("errorMessage", exception.getMessage());
        if (exception instanceof ValidationException) {
            logger.debug("Validation Error in CovidVaccinationController:" + exception.getMessage());
        } else {
            logger.error("Exception in CovidVaccinationController:", exception);
        }
        return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
    }
}
