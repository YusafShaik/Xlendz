package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.responses.EmployeeIndustryResponse;
import in.xlendz.service.EmployeeIndustryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employment-details")
public class EmploymentSectorController {

    private static final Logger LOGGER= LoggerFactory.getLogger(EmploymentSectorController.class);
    private final  EmployeeIndustryService employeeIndustryService;

    @Autowired
    public EmploymentSectorController(EmployeeIndustryService employeeIndustryService) {
        this.employeeIndustryService = employeeIndustryService;
    }
    @PostMapping("/create-industry")
    public ResponseEntity<MethodResponse> createSector(@Valid @NotBlank @RequestParam String industryName) {
        LOGGER.info("Received request to create industry: {}", industryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeIndustryService.saveIndustry(industryName));
    }
    @GetMapping("/get-industries")
    public ResponseEntity<List<EmployeeIndustryResponse>> getSectors(){
        LOGGER.info("Received request to get sectors");
        return  ResponseEntity.ok(employeeIndustryService.getSectors());
    }
}
