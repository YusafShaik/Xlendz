package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.LoanRequestStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.LenderLoanProposal;
import in.xlendz.entity.UserLoanRequest;
import in.xlendz.entity.XlendzUser;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.exception.KYCNotVerifiedException;
import in.xlendz.repo.LoanProposalRepository;
import in.xlendz.repo.UserLoanRequestRepository;
import in.xlendz.requests.UserLoanRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLoanRequestServiceImpl implements UserLoanRequestService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoanRequestServiceImpl.class);

    private final UserLoanRequestRepository userLoanRequestRepository;
    private final UserDetailsService userDetailsService;

    private final LoanProposalRepository loanProposalRepository;

    @Autowired
    public UserLoanRequestServiceImpl(UserLoanRequestRepository userLoanRequestRepository,
                                      UserDetailsService userDetailsService, LoanProposalRepository loanProposalRepository) {
        this.userLoanRequestRepository = userLoanRequestRepository;
        this.userDetailsService = userDetailsService;
        this.loanProposalRepository = loanProposalRepository;
    }

    @Override
    public MethodResponse createLoanRequest(UserLoanRequestDto userLoanRequestDto) {
        try{
            LOGGER.info("Creating loan request for user: {}", userLoanRequestDto.getUserId());
            XlendzUser xlendzUser=userDetailsService.getUserByUserId(userLoanRequestDto.getUserId());
            if(Boolean.FALSE.equals(xlendzUser.getIsVerified())){
                throw new KYCNotVerifiedException(ExceptionConstants.KYC_NOT_VERIFIED);
            }
           LOGGER.info("User found: {}", xlendzUser.getXlendzUserId());
            userLoanRequestRepository.save(getLoanObjBuild(userLoanRequestDto, xlendzUser));
            LOGGER.info("Loan request created successfully for user: {}", userLoanRequestDto.getUserId());
            return MethodResponse.SUCCESS;
        }catch (DataUpdationException e){
            throw new DataUpdationException(ExceptionConstants.LOAN_REQUEST_CREATION_FAILED);
        }
    }

    @Override
    public MethodResponse updateLoanRequest(Long userId, Long loanRequestId) {
        try{
            LOGGER.info("Updating loan request for user: {}", userId);
            Optional<UserLoanRequest> userLoanRequest =
                    userLoanRequestRepository.findByXlendzUser_XlendzUserIdAndLoanRequestId(userId, loanRequestId);
            if(userLoanRequest.isPresent()){
                LOGGER.info("Loan request found: {}", loanRequestId);
                if(!userLoanRequest.get().getLoanRequestStatus().equals(LoanRequestStatus.OPEN)){
                    throw new DataUpdationException(ExceptionConstants.LOAN_REQUEST_ALREADY_CLOSED);
                }
                userLoanRequest.get().setLoanRequestStatus(LoanRequestStatus.CLOSED);
                userLoanRequestRepository.save(userLoanRequest.get());
                LOGGER.info("Loan request updated successfully: {}", loanRequestId);
                return MethodResponse.SUCCESS;
            }
            throw new DataUpdationException(ExceptionConstants.LOAN_REQUEST_NOT_FOUND);
        }catch (DataRetrievalException e){
            throw new DataRetrievalException(ExceptionConstants.ERROR_WHILE_FETCHING_USER_DETAILS,e);
        }catch (DataUpdationException e){
            throw new DataUpdationException(ExceptionConstants.LOAN_REQUEST_UPDATION_FAILED);
        }
    }

    private static UserLoanRequest getLoanObjBuild(UserLoanRequestDto userLoanRequestDto, XlendzUser xlendzUser) {
        LOGGER.info("Building loan request object for user: {}", userLoanRequestDto.getUserId());
        return UserLoanRequest.builder()
                .loanType(userLoanRequestDto.getLoanType())
                .xlendzUser(xlendzUser)
                .loanAmount(userLoanRequestDto.getLoanAmount())
                .loanRequestStatus(LoanRequestStatus.OPEN)
                .build();
    }
}
