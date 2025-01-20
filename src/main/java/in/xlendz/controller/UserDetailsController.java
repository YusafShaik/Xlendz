package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.XlendzUserDetailRequest;
import in.xlendz.service.UserDetailsSaveService;
import in.xlendz.requests.EmploymentDetailRequest;
import in.xlendz.requests.UserIdentityDetailsRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xlendzVerification")
public class UserDetailsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsController.class);
    private final UserDetailsSaveService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsSaveService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/saveBasicDetails")
    public ResponseEntity<MethodResponse> submitUserDetails(@Valid @RequestBody XlendzUserDetailRequest
                                                                    xlendzUserDetailRequest) {
        LOGGER.info("Received request to save user basic details");
        return ResponseEntity.ok(userDetailsService.saveUserBasicDetails(xlendzUserDetailRequest));
    }

    @PostMapping("/identity")
    public ResponseEntity<MethodResponse> saveUserIdentityDetails(@Valid @RequestBody UserIdentityDetailsRequest
                                                                          userIdentityDocRequest) {
        LOGGER.info("Received request to save user identity details");
        return ResponseEntity.ok(userDetailsService.saveUserIdentityDetails(userIdentityDocRequest));
    }

    @PostMapping("/employment")
    public ResponseEntity<MethodResponse> saveUserEmploymentDetails(@Valid @RequestBody EmploymentDetailRequest employmentDetailRequest) {
        LOGGER.info("Received request to save user employment details");
        return ResponseEntity.ok(userDetailsService.saveUserEmploymentDetails(employmentDetailRequest));
    }

}
