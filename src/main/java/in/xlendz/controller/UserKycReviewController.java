package in.xlendz.controller;

import in.xlendz.constants.KycStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.KycReviewStatusUpdateRequest;
import in.xlendz.requests.UserDetailsFeedbackRequest;
import in.xlendz.responses.KycReviewListResponse;
import in.xlendz.responses.UserDetailsResponse;
import in.xlendz.service.KycReviewQueryService;
import in.xlendz.service.KycReviewUpdateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/kyc")
public class UserKycReviewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserKycReviewController.class);
    private final KycReviewQueryService kycReviewService;
    private final KycReviewUpdateService kycReviewUpdateService;

    @Autowired
    public UserKycReviewController(KycReviewQueryService kycReviewService, KycReviewUpdateService kycReviewUpdateService) {
        this.kycReviewService = kycReviewService;
        this.kycReviewUpdateService = kycReviewUpdateService;
    }

    @GetMapping("/review-List")
    public ResponseEntity<List<KycReviewListResponse>> getKycReviewList() {
        LOGGER.info("Received request to get kyc review list");
        return ResponseEntity.ok( kycReviewService.getKycReviewList());
    }

    @GetMapping("/review/{emailId}")
    public ResponseEntity<UserDetailsResponse> getKycReviewByEmailId(@Valid @NotBlank @PathVariable String emailId) {
        LOGGER.info("Received request to get kyc review by email id: {}", emailId);
        return ResponseEntity.ok(kycReviewService.getKycReviewByEmailId(emailId));
    }

    @PostMapping("/comments")
    public ResponseEntity<MethodResponse> userDetailsFeedBacK(@Valid @RequestBody UserDetailsFeedbackRequest userDetailsFeedbackRequest) {
        LOGGER.info("Received request to update user details feedback");
        return ResponseEntity.ok(kycReviewUpdateService.userDetailsReview(userDetailsFeedbackRequest));
    }

    @PostMapping("/update-kyc-status")
    public ResponseEntity<KycStatus> updateKycStatus(@RequestBody KycReviewStatusUpdateRequest kycReviewStatusUpdateRequest) {
        LOGGER.info("Received request to update kyc status");
        return ResponseEntity.ok(kycReviewUpdateService.updateKycStatus(kycReviewStatusUpdateRequest));
    }

    @PostMapping("/approve-kyc")
    public ResponseEntity<MethodResponse> approveKyc(@NotBlank @RequestParam String userId) {
        LOGGER.info("Received request to approve kyc");
        return ResponseEntity.ok(kycReviewUpdateService.approveKyc(userId));
    }
}
