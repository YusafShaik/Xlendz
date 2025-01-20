package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.LoanRequestStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.ProposalStatus;
import in.xlendz.entity.LenderLoanProposal;
import in.xlendz.entity.UserLoanRequest;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.LoanProposalRepository;
import in.xlendz.repo.UserLoanRequestRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserLoanRequestProposalServiceImpl implements UserLoanRequestProposalService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoanRequestProposalServiceImpl.class);

    private final LoanProposalRepository loanProposalRepository;

    private final UserLoanRequestRepository userLoanRequestRepository;
    @Autowired
    public UserLoanRequestProposalServiceImpl(LoanProposalRepository loanProposalRepository, UserLoanRequestRepository userLoanRequestRepository) {
        this.loanProposalRepository = loanProposalRepository;
        this.userLoanRequestRepository = userLoanRequestRepository;
    }

    @Override
    @Transactional
    public MethodResponse userLoanRequestProposalService(Long userId, Long proposalId) {
        try {
            LOGGER.info("Accepting proposal: {} for user: {}", proposalId, userId);
            LenderLoanProposal lenderLoanProposal =
                    loanProposalRepository.findByLoanProposalId(proposalId).orElseThrow(() ->
                            new DataRetrievalException(ExceptionConstants.LOAN_PROPOSAL_NOT_FOUND));
            LOGGER.info("Proposal found: {}", proposalId);
            UserLoanRequest userLoanRequest =
                    userLoanRequestRepository.findByXlendzUser_XlendzUserIdAndLoanRequestStatus(userId,
                            LoanRequestStatus.OPEN).orElseThrow(
                                    () -> new DataRetrievalException(ExceptionConstants.LOAN_REQUEST_NOT_FOUND));
            LOGGER.info("Loan request found for user: {}", userId);
            loanProposalRepository.updateProposalStatusByLoanRequestId(ProposalStatus.ACCEPTED_OTHER_OFFER,
                    lenderLoanProposal.getLoanRequestId(), proposalId);

            lenderLoanProposal.setProposalStatus(ProposalStatus.ACCEPTED);

            userLoanRequest.setLoanRequestStatus(LoanRequestStatus.CLOSED);

            loanProposalRepository.save(lenderLoanProposal);
            LOGGER.info("Proposal status updated successfully: {}", proposalId);
            userLoanRequestRepository.save(userLoanRequest);
            LOGGER.info("Loan request status updated successfully for user: {}", userId);
            return MethodResponse.SUCCESS;
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(e.getMessage(), e);
        } catch (DataUpdationException e) {
            throw new DataUpdationException(e.getMessage(), e);
        }
    }



}
