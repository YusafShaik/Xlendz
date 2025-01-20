package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.LenderBasicDetails;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.repo.LenderRepo;
import in.xlendz.repo.LenderBasicDetailsRepo;
import in.xlendz.requests.LenderBasicDetailsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LenderBasicDetailsServiceImpl implements LenderBasicDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderBasicDetailsServiceImpl.class);
    private final LenderBasicDetailsRepo xlendzLenderBasicDetailsRepo;

    private final LenderRepo lenderRepo;

    @Autowired
    public LenderBasicDetailsServiceImpl(LenderBasicDetailsRepo xlendzLenderBasicDetailsRepo,
                                         LenderRepo lenderRepo
    ) {
        this.xlendzLenderBasicDetailsRepo = xlendzLenderBasicDetailsRepo;
        this.lenderRepo = lenderRepo;

    }

    @Override
    public MethodResponse saveLenderBasicDetails(LenderBasicDetailsRequest lenderBasicDetailsRequest) {
        try {
            LOGGER.info("saveLenderBasicDetails() method execution started");
            Optional<XlendzLender> lender = lenderRepo.findByEmail(lenderBasicDetailsRequest.getLenderEmail());
            if (lender.isPresent()) {
                LOGGER.info("Lender is present with the email : {} " , lenderBasicDetailsRequest.getLenderEmail());
                LenderBasicDetails lenderBasicDetails = buildBasicDetailsObj(lenderBasicDetailsRequest, lender.get());
                xlendzLenderBasicDetailsRepo.save(lenderBasicDetails);
                LOGGER.info("Lender Basic Details saved successfully");
                return MethodResponse.SUCCESS;
            } else {
                throw new UserNotFoundException(ExceptionConstants.LENDER_NOT_FOUND + lenderBasicDetailsRequest.getLenderEmail());
            }
        } catch (DataRetrievalException | UserNotFoundException e) {
            throw new DataRetrievalException(e.getMessage());
        }
    }

    private static LenderBasicDetails buildBasicDetailsObj(LenderBasicDetailsRequest lenderBasicDetailsRequest,
                                                           XlendzLender lender) {
        return LenderBasicDetails.builder()
                .xlendzLender(lender)
                .lenderBankingType(lenderBasicDetailsRequest.getLenderBankingType())
                .companyName(lenderBasicDetailsRequest.getCompanyName())
                .dateOfInCorporation(lenderBasicDetailsRequest.getDateOfInCorporation())
                .registrationNumber(lenderBasicDetailsRequest.getRegistrationNumber())
                .build();
    }


}
