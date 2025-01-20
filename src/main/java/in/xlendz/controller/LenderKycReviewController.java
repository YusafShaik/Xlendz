package in.xlendz.controller;

import in.xlendz.constants.KycStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LenderKycCommentRequest;
import in.xlendz.responses.KycReviewListResponse;
import in.xlendz.responses.LenderDetailsDTO;
import in.xlendz.responses.LenderKycDetailsComments;
import in.xlendz.service.LenderDetailsService;
import in.xlendz.service.LenderKycQueryService;
import in.xlendz.service.LenderKycUpdateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/kyc/lender")
public class LenderKycReviewController {

    private final LenderDetailsService lenderDetailsService;
    private final LenderKycUpdateService lenderKycUpdateService;
    private final LenderKycQueryService lenderKycQueryService;

    @Autowired
    public LenderKycReviewController(LenderDetailsService lenderDetailsService, LenderKycUpdateService lenderKycService, LenderKycQueryService lenderKycQueryService) {
        this.lenderDetailsService = lenderDetailsService;
        this.lenderKycUpdateService = lenderKycService;
        this.lenderKycQueryService = lenderKycQueryService;
    }

    @RequestMapping("/details")
    public ResponseEntity<LenderDetailsDTO> getLenderDetails(@RequestParam String lenderEmail){
        return ResponseEntity.ok(lenderDetailsService.getLenderDetails(lenderEmail));
    }

    @GetMapping("/details-remarks")
    public ResponseEntity<LenderKycDetailsComments> getLenderKycRemarks(@RequestParam String lenderEmail){
        return ResponseEntity.ok(lenderKycQueryService.getLenderKycRemarks(lenderEmail));
    }

    @RequestMapping("/list")
    public ResponseEntity<List<KycReviewListResponse>> getLenderKycDetails(){
        return ResponseEntity.ok(lenderKycQueryService.getLenderKycList());
    }

    @RequestMapping("/submit")
    public ResponseEntity<MethodResponse> submitLenderKyc(@RequestParam String lenderEmail){
        return ResponseEntity.ok(lenderKycUpdateService.submitLenderKyc(lenderEmail));
    }

    @RequestMapping("/comments")
    public ResponseEntity<MethodResponse> lenderKycComments(@Valid @RequestBody LenderKycCommentRequest lenderKycCommentRequest){
        return ResponseEntity.ok(lenderKycUpdateService.lenderKycComments(lenderKycCommentRequest));
    }

    @RequestMapping("/approve")
    public ResponseEntity<MethodResponse> lenderKycApprove(@RequestParam String lenderEmail){
        return ResponseEntity.ok(lenderKycUpdateService.lenderKycApprove(lenderEmail));
    }

    @RequestMapping("/update-status")
    public ResponseEntity<MethodResponse> updateLenderKycStatus(@RequestParam  String lenderEmail, KycStatus kycStatus){
        return ResponseEntity.ok(lenderKycUpdateService.updateLenderKycStatus(lenderEmail, kycStatus));
    }
}
