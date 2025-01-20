package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.UserLoanRequestDto;
import in.xlendz.responses.UserLoanRequestProposalsDTO;
import in.xlendz.service.UserLoanQueryService;
import in.xlendz.service.UserLoanRequestProposalService;
import in.xlendz.service.UserLoanRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user-loan-request")
@RestController
public class UserLoanRequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoanRequestController.class);

    private  final UserLoanRequestService userLoanRequestService;
    private final UserLoanQueryService userLoanQueryService;
    private final UserLoanRequestProposalService userLoanRequestProposalService;

    @Autowired
    public UserLoanRequestController(UserLoanRequestService userLoanRequestService, UserLoanQueryService userLoanQueryService, UserLoanRequestProposalService userLoanRequestProposalService) {
        this.userLoanRequestService = userLoanRequestService;
        this.userLoanQueryService = userLoanQueryService;
        this.userLoanRequestProposalService = userLoanRequestProposalService;
    }

    @PostMapping("/create")
    public ResponseEntity<MethodResponse> createLoanRequest(@Valid @RequestBody UserLoanRequestDto userLoanRequestDto){
        LOGGER.info("Received request to create loan request: {}", userLoanRequestDto);
        return ResponseEntity.ok(userLoanRequestService.createLoanRequest(userLoanRequestDto));
    }

    @PostMapping("/update")
    public ResponseEntity<MethodResponse> updateLoanRequest(@RequestParam @NotNull @Positive Long userId,
                                                            @RequestParam @NotNull @Positive Long loanRequestId) {
        LOGGER.info("Received request to update loan request: {}", userId);
        return ResponseEntity.ok(userLoanRequestService.updateLoanRequest(userId, loanRequestId));
    }

    @GetMapping("/get-loan-proposals")
    public ResponseEntity<UserLoanRequestProposalsDTO> getUserLoanProposals(@RequestParam @NotNull @Positive Long userId){
        LOGGER.info("Received request to get loan proposals: {}", userId);
        return ResponseEntity.ok(userLoanQueryService.getLoanProposals(userId));
    }

    @PostMapping("/accept-proposal")
    public ResponseEntity<MethodResponse> acceptProposal(@RequestParam @NotNull Long userId, @RequestParam @NotNull Long proposalId){
        LOGGER.info("Received request to accept proposal: {}", userId);
        return ResponseEntity.ok(userLoanRequestProposalService.userLoanRequestProposalService(userId, proposalId));
    }


}
