package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.LenderBankDetails;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.repo.LenderBankDetailsRepo;
import in.xlendz.repo.LenderRepo;
import in.xlendz.requests.LenderBankDetailsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LenderBankDetailsServiceImpl implements LenderBankDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderBankDetailsServiceImpl.class);
    private final LenderRepo lenderRepo;
    private final LenderBankDetailsRepo lenderBankDetailsRepo;

    @Autowired
    public LenderBankDetailsServiceImpl(LenderRepo lenderRepo, LenderBankDetailsRepo lenderBankDetailsRepo) {
        this.lenderRepo = lenderRepo;
        this.lenderBankDetailsRepo = lenderBankDetailsRepo;
    }

    @Override
    public MethodResponse saveBankDetails(LenderBankDetailsRequest lenderBankDetailsRequest) {
        try {
            LOGGER.info("Received request to save bank details, lenderBankDetailsRequest={}", lenderBankDetailsRequest.getLenderEmail());
            Optional<XlendzLender> lender = lenderRepo.findByEmail(lenderBankDetailsRequest.getLenderEmail());
            if(lender.isPresent()){
                LOGGER.info("Lender with email {} found", lenderBankDetailsRequest.getLenderEmail());
                LenderBankDetails lenderBankDetails = buildBankDetailsObj(lenderBankDetailsRequest, lender.get());
                lenderBankDetailsRepo.save(lenderBankDetails);
                LOGGER.info("Bank details saved successfully");
                return MethodResponse.SUCCESS;
            }else{
                LOGGER.warn("Lender with email {} not found",lenderBankDetailsRequest.getLenderEmail());
                throw new UserNotFoundException("Lender with email " + lenderBankDetailsRequest.getLenderEmail() + " not found");
            }
        } catch (DataRetrievalException  | UserNotFoundException e) {
            throw new DataRetrievalException(e.getMessage());
        }

    }

    private  LenderBankDetails buildBankDetailsObj(LenderBankDetailsRequest lenderBankDetailsRequest, XlendzLender lender) {
        LOGGER.info("Building bank details object for lender with email {}", lender.getEmail());
        return LenderBankDetails.builder()
                .lender(lender)
                .accountNumber(lenderBankDetailsRequest.getAccountNumber())
                .bankName(lenderBankDetailsRequest.getBankName())
                .ifscCode(lenderBankDetailsRequest.getIfscCode())
                .build();
    }
}
