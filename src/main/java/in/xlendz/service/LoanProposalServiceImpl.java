package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.ProposalStatus;
import in.xlendz.entity.LenderLoanProposal;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.LoanProposalRepository;
import in.xlendz.requests.LoanProposalDTO;
import in.xlendz.requests.LoanProposalUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class LoanProposalServiceImpl implements LoanProposalService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanProposalServiceImpl.class);
    private final LoanProposalRepository loanProposalRepository;
    private final LenderDetailsService lenderDetailsService;

    @Autowired
    public LoanProposalServiceImpl(LoanProposalRepository loanProposalRepository, LenderDetailsService lenderDetailsService) {
        this.loanProposalRepository = loanProposalRepository;
        this.lenderDetailsService = lenderDetailsService;
    }

    @Override
    public MethodResponse submitLoanProposal(LoanProposalDTO loanProposalDTO) {
        try{
            LOGGER.info("submitLoanProposal() called");
            loanProposalRepository.save(getLoanProposalBuild(loanProposalDTO));
            LOGGER.info("Loan proposal submitted successfully");
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException exception){
            throw new DataRetrievalException(exception.getMessage());
        }catch (DataUpdationException exception){
        throw new DataUpdationException(exception.getMessage());
        }
    }

    @Override
    public MethodResponse updateLoanProposal(LoanProposalUpdateDTO loanProposalDTO) {
        try{
            LOGGER.info("updateLoanProposal() called");
            LenderLoanProposal loanProposal =
                    loanProposalRepository.findByLoanProposalId(loanProposalDTO.getLoanProposalId()).orElseThrow(() -> new
                            DataRetrievalException(ExceptionConstants.LOAN_PROPOSAL_NOT_FOUND + loanProposalDTO.getLoanProposalId()));
            updateFieldIfNonZero(loanProposalDTO.getLoanAmount(), loanProposal::setLoanAmount);
            updateFieldIfNonZero(loanProposalDTO.getRateOfInterest(), loanProposal::setRateOfInterest);
            updateFieldIfNonZero(loanProposalDTO.getTenure(), loanProposal::setTenure);
            updateFieldIfNonZero(loanProposalDTO.getProcessingFees(), loanProposal::setProcessingFees);
            loanProposalRepository.save(loanProposal);
            LOGGER.info("Loan proposal updated successfully");
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException exception){
            throw new DataRetrievalException(exception.getMessage());
        }catch (DataUpdationException exception){
            throw new DataUpdationException(exception.getMessage());
        }
    }

    private <T extends Number> void updateFieldIfNonZero(T newValue, Consumer<T> updater) {
        LOGGER.info("updateFieldIfNonZero() called");
        if (newValue.doubleValue() != 0) {
            updater.accept(newValue);
        }
    }
    
    private LenderLoanProposal getLoanProposalBuild(LoanProposalDTO loanProposalDTO) {
        LOGGER.info("getLoanProposalBuild() called");
        return LenderLoanProposal.builder().
                loanRequestId(loanProposalDTO.getLoanRequestId())
                .xlendzLender(lenderDetailsService.getLenderByEmail(loanProposalDTO.getLenderEmail()))
                .loanAmount(loanProposalDTO.getLoanAmount())
                .rateOfInterest(loanProposalDTO.getRateOfInterest())
                .tenure(loanProposalDTO.getTenure())
                .processingFees(loanProposalDTO.getProcessingFees())
                .proposalStatus(ProposalStatus.PENDING)
                .build();
    }
}
