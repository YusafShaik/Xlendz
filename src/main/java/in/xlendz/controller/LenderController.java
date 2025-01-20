package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.*;
import in.xlendz.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lender")
public class LenderController {

    private static final Logger LOGGER= LoggerFactory.getLogger(LenderController.class);
    private final LenderSignUpService lenderSignUpService;
    private final LenderBasicDetailsService lenderBasicDetailsService;
    private final LenderBankDetailsService lenderBankDetailsService;
    private final LenderDocumentsService lenderDocumentsService;
    private final LenderPersonnelService lenderPersonnelService;
    private final LenderSecurityCompService lenderSecurityCompService;

    @Autowired
    public LenderController(LenderSignUpService lenderSignUpService, LenderBasicDetailsService xlendzLenderDetails, LenderBankDetailsService lenderBankDetailsService, LenderDocumentsService lenderFilesService, LenderPersonnelService lenderPersonnelService, LenderSecurityCompService lenderSecurityCompeService) {
        this.lenderSignUpService = lenderSignUpService;
        this.lenderBasicDetailsService = xlendzLenderDetails;
        this.lenderBankDetailsService = lenderBankDetailsService;
        this.lenderDocumentsService = lenderFilesService;
        this.lenderPersonnelService = lenderPersonnelService;
        this.lenderSecurityCompService = lenderSecurityCompeService;
    }


    @PostMapping("/signup")
    public ResponseEntity<MethodResponse> lenderSignUp(@Valid @RequestBody LenderSignUpRequest  lenderSignUpRequest) {
        LOGGER.info("Received request to sign up lender, lenderSignUpRequest={}",lenderSignUpRequest.getLenderEmail());
        MethodResponse response = lenderSignUpService.lenderSignUp(lenderSignUpRequest);
        if (response == MethodResponse.SUCCESS) {
            return ResponseEntity.status(201).body(response);
        } else if (response == MethodResponse.USER_EXISTS) {
            return ResponseEntity.status(409).body(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/basic-details")
    public ResponseEntity<MethodResponse> saveLenderBasicDetails(@Valid @RequestBody LenderBasicDetailsRequest lenderBasicDetailsRequest){
        LOGGER.info("Received request to save lender basic details, lenderBasicDetailsRequest={}", lenderBasicDetailsRequest.getLenderEmail());
        return ResponseEntity.ok(lenderBasicDetailsService.saveLenderBasicDetails(lenderBasicDetailsRequest));
    }

    @PostMapping("/registration-docs")
    public ResponseEntity<MethodResponse> saveLenderRegistrationDocs(@Valid @ModelAttribute LenderFilesUploadRequest lenderFilesUploadRequest){
       LOGGER.info("Received request to save lender registration docs, lenderFilesUploadRequest={}", lenderFilesUploadRequest.getLenderEmail());
        return ResponseEntity.ok( lenderDocumentsService.saveLenderRegistrationDocs(lenderFilesUploadRequest));
    }

    @PostMapping("/signatories")
    public ResponseEntity<MethodResponse> saveLendorSignatories(@Valid @RequestBody LenderSignatoriesRequest lendersignatoriesRequest) {
        LOGGER.info("Received request to save lender signatories, lenderSignatoriesRequest={}",
                lendersignatoriesRequest.getLenderEmail());
        return ResponseEntity.ok(lenderPersonnelService.saveLendorSignatories(lendersignatoriesRequest));
    }

    @PostMapping("/key-personnel")
    public ResponseEntity<MethodResponse> saveKeyPersonnel(@Valid @ModelAttribute LenderKeyPersonsRequest lenderKeyPersonsRequest){
        LOGGER.info("Received request to save key personnel, lenderKeyPersonsRequest={}", lenderKeyPersonsRequest.getLenderEmail());
        return ResponseEntity.ok( lenderPersonnelService.saveKeyPersonnel(lenderKeyPersonsRequest));
    }

    @PostMapping("/company-profile")
    public ResponseEntity<MethodResponse> saveCompanyProfile(@Valid @ModelAttribute CompanyProfileRequest companyProfileRequest) {
        LOGGER.info("Received request to save company profile, companyProfileRequest={}",
                companyProfileRequest.getLenderEmail());
        return ResponseEntity.ok(lenderDocumentsService.saveCompanyProfile(companyProfileRequest));
    }

    @PostMapping("/tax-info")
    public ResponseEntity<MethodResponse> saveLenderTaxInfo(@Valid @ModelAttribute LenderTaxInfoRequest lenderTaxInfoRequest){
        LOGGER.info("Received request to save lender tax info, lenderTaxInfoRequest={}", lenderTaxInfoRequest.getLenderEmail());
        return ResponseEntity.ok(lenderDocumentsService.saveLenderTaxInfo(lenderTaxInfoRequest));
    }
    @PostMapping("/security-compliance")
    public ResponseEntity<MethodResponse> saveSecurityCompliance(@Valid @ModelAttribute SecurityComplianceRequest securityComplianceRequest){
        LOGGER.info("Received request to save security compliance, securityComplianceRequest={}", securityComplianceRequest.getLenderEmail());
        return ResponseEntity.ok(lenderSecurityCompService.saveSecurityCompliance(securityComplianceRequest));
    }

    @PostMapping("/bank-details")
    public ResponseEntity<MethodResponse> saveBankDetails(@RequestBody LenderBankDetailsRequest lenderBankDetailsRequest){
        LOGGER.info("Received request to save bank details, lenderBankDetailsRequest={}", lenderBankDetailsRequest.getLenderEmail());
        return ResponseEntity.ok(lenderBankDetailsService.saveBankDetails(lenderBankDetailsRequest));
    }
}
