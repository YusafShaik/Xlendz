package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LoanProposalDTO;
import in.xlendz.requests.LoanProposalUpdateDTO;
import in.xlendz.responses.UserLoanRequestResponse;
import in.xlendz.service.LoanProposalService;
import in.xlendz.service.UserLoanQueryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/loan-proposal")
@RestController
public class LoanProposalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanProposalController.class);
    private final LoanProposalService loanProposalService;

    private final UserLoanQueryService userLoanQueryService;
    @Autowired
    public LoanProposalController(LoanProposalService loanProposalService, UserLoanQueryService userLoanQueryService) {
        this.loanProposalService = loanProposalService;
        this.userLoanQueryService = userLoanQueryService;
    }

    @RequestMapping("/submit-loan-proposal")
    public ResponseEntity<MethodResponse> submitLoanProposal(@Valid @RequestBody LoanProposalDTO loanProposalDTO){
        LOGGER.info("submitLoanProposal() called");
        return ResponseEntity.ok(loanProposalService.submitLoanProposal(loanProposalDTO));
    }

    @RequestMapping("/update-loan-proposal")
    public ResponseEntity<MethodResponse> updateLoanProposal(@Validated @RequestBody LoanProposalUpdateDTO loanProposalDTO){
        LOGGER.info("updateLoanProposal() called");
        return ResponseEntity.ok(loanProposalService.updateLoanProposal(loanProposalDTO));
    }

    @RequestMapping("/getAll-loan-requests")
    public ResponseEntity<List<UserLoanRequestResponse>> getAllLoanRequests(){
        LOGGER.info("getAllLoanRequests() called");
        return ResponseEntity.ok(userLoanQueryService.getAllLoanRequests());
    }



}
