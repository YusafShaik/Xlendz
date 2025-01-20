package in.xlendz.controller;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.CreateXlendzUserRequest;
import in.xlendz.responses.UserDetailsFeedbackResponse;
import in.xlendz.service.UserCreationService;
import in.xlendz.responses.UserDetailsResponse;
import in.xlendz.service.UserDetailsService;
import in.xlendz.service.UserKycReviewService;
import in.xlendz.service.UserKycSubmitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);
    private final UserCreationService userCreationService;
    private final UserDetailsService userDetailsService;
    private final UserKycReviewService userKycReviewService;
    private final UserKycSubmitService userKycSubmitService;

    public UserController(UserCreationService xlendzUserService, UserDetailsService userDetailsService,
                          UserKycReviewService userKycReviewService, UserKycSubmitService userKycSubmitService) {
        this.userCreationService = xlendzUserService;
        this.userDetailsService = userDetailsService;
        this.userKycReviewService = userKycReviewService;
        this.userKycSubmitService = userKycSubmitService;
    }

    @PostMapping("/create")
    public ResponseEntity<MethodResponse> createXlendzUser(@Valid @RequestBody CreateXlendzUserRequest userRequest) {
        LOGGER.info("Received request to create user for: {}", userRequest.getEmail());
        MethodResponse response = userCreationService.createXlendzUser(userRequest);
        if(response==MethodResponse.SUCCESS){
            LOGGER.info("Xlendz User created successfully: {}", userRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        LOGGER.error("Failed to create Xlendz User: {}", userRequest.getEmail());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user-details")
    public ResponseEntity<UserDetailsResponse> getUserDetails( @NotBlank(message = ExceptionConstants.USER_ID_BLANK)@RequestParam String xlendzUserId){
        LOGGER.info("Received request to get user details for: {}", xlendzUserId);
        UserDetailsResponse userDetails= userDetailsService.getUserDetails(xlendzUserId);
        if(userDetails!=null){
            LOGGER.info("User details retrieved successfully for: {}", xlendzUserId);
            return  ResponseEntity.ok(userDetails);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/kyc-comments")
    public ResponseEntity<UserDetailsFeedbackResponse> getUserKycReviewDetails(@NotBlank(message = ExceptionConstants.USER_ID_BLANK)
                                                                               @RequestParam String xlendzUserId) {
        LOGGER.info("Received request to get user KYC review details, userId={}", xlendzUserId);
        return ResponseEntity.ok(userKycReviewService.getUserKycReviewDetails(xlendzUserId));
    }

    @PostMapping("/submit-profile")
    public ResponseEntity<MethodResponse> submitUserProfile(@NotBlank(message = ExceptionConstants.USER_ID_BLANK)
                                                        @RequestParam String xlendzUserId) {
        LOGGER.info("Received request to submit profile, userId={}", xlendzUserId);
        return ResponseEntity.ok(userKycSubmitService.submitUserProfile(xlendzUserId));
    }
}
