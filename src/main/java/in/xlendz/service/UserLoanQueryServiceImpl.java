package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.LoanRequestStatus;
import in.xlendz.entity.LenderLoanProposal;
import in.xlendz.entity.UserLoanRequest;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.repo.LenderBasicDetailsRepo;
import in.xlendz.repo.LoanProposalRepository;
import in.xlendz.repo.UserLoanRequestRepository;
import in.xlendz.responses.LenderLoanProposalResponse;
import in.xlendz.responses.UserLoanRequestProposalsDTO;
import in.xlendz.responses.UserLoanRequestResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserLoanQueryServiceImpl implements UserLoanQueryService{

    private static final Logger LOGGER= LoggerFactory.getLogger(UserLoanQueryServiceImpl.class);

    private final LoanProposalRepository loanProposalRepository;
    private final UserLoanRequestRepository userLoanRequestRepository;
    private final LenderBasicDetailsRepo lenderBasicDetailsRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public UserLoanQueryServiceImpl(LoanProposalRepository loanProposalRepository,
                                    UserLoanRequestRepository userLoanRequestRepository,
                                    LenderBasicDetailsRepo lenderBasicDetailsRepo, ModelMapper modelMapper) {
        this.loanProposalRepository = loanProposalRepository;
        this.userLoanRequestRepository = userLoanRequestRepository;
        this.lenderBasicDetailsRepo = lenderBasicDetailsRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserLoanRequestProposalsDTO getLoanProposals(Long userId) {
        try {
           LOGGER.info("Fetching loan proposals for user: {}", userId);
            UserLoanRequest userLoanRequest = fetchUserLoanRequest(userId);
            Map<Long, String> lenderDetailsMap = fetchLenderDetails();
            List<LenderLoanProposalResponse> proposalResponses = processLoanProposals(userLoanRequest, lenderDetailsMap);
            LOGGER.info("Loan proposals fetched successfully for user: {}", userId);
            return buildLoanRequestProposalsDTO(userLoanRequest, proposalResponses);
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(ExceptionConstants.SOME_INTERNAL_ERROR_IS_OCCURRED, e);
        }
    }

    @Override
    public List<UserLoanRequestResponse> getAllLoanRequests() {
        try{
            LOGGER.info("Fetching all loan requests");
            List<UserLoanRequest> request=userLoanRequestRepository.findAllByLoanRequestStatus(LoanRequestStatus.OPEN);
            return request.stream().map(e->modelMapper.map(e,UserLoanRequestResponse.class)).toList();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException(ExceptionConstants.SOME_INTERNAL_ERROR_IS_OCCURRED,e);
        }
    }

    private UserLoanRequest fetchUserLoanRequest(Long userId) {
        LOGGER.info("Fetching user loan request for user ID: {}", userId);
        return userLoanRequestRepository
                .findByXlendzUser_XlendzUserIdAndLoanRequestStatus(userId, LoanRequestStatus.OPEN)
                .orElseThrow(() -> new DataRetrievalException(ExceptionConstants.LOAN_REQUEST_NOT_FOUND));
    }


    private Map<Long, String> fetchLenderDetails() {
        LOGGER.info("Fetching lender details");
        return lenderBasicDetailsRepo.findCompanyNamesAndLenderIds()
                .stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[1],
                        result -> (String) result[0],
                        (existing, replacement) -> existing
                ));
    }

    private List<LenderLoanProposalResponse> processLoanProposals(
            UserLoanRequest userLoanRequest,
            Map<Long, String> lenderDetailsMap) {
        LOGGER.info("Processing loan proposals for user loan request ID: {}", userLoanRequest.getLoanRequestId());
        List<LenderLoanProposal> loanProposals = loanProposalRepository
                .findByLoanRequestId(userLoanRequest.getLoanRequestId());

        return loanProposals.parallelStream()
                .map(proposal -> mapToLoanProposalResponse(proposal, lenderDetailsMap))
                .collect(Collectors.toList());
    }

    private LenderLoanProposalResponse mapToLoanProposalResponse(
            LenderLoanProposal proposal,
            Map<Long, String> lenderDetailsMap) {
        LenderLoanProposalResponse response = modelMapper.map(proposal, LenderLoanProposalResponse.class);
        response.setLenderName(lenderDetailsMap.get(proposal.getXlendzLender().getLenderId()));
        return response;
    }

    private UserLoanRequestProposalsDTO buildLoanRequestProposalsDTO(
            UserLoanRequest userLoanRequest,
            List<LenderLoanProposalResponse> proposalResponses) {
        LOGGER.info("Building loan request proposals DTO for user loan request ID: {}", userLoanRequest.getLoanRequestId());
        return UserLoanRequestProposalsDTO.builder()
                .userLoanRequestResponse(modelMapper.map(userLoanRequest, UserLoanRequestResponse.class))
                .lenderLoanProposalResponseList(proposalResponses)
                .build();
    }


}
