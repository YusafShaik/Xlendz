package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.LenderSecurityComplianceDocs;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.InvalidFileException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.LenderSecurityRepo;
import in.xlendz.requests.SecurityComplianceRequest;
import in.xlendz.util.FIleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LenderSecurityCompServiceImpl implements LenderSecurityCompService{

    private static final Logger LOGGER= LoggerFactory.getLogger(LenderSecurityCompServiceImpl.class);
    private final LenderSecurityRepo lenderSecurityRepo;
    private final LenderDetailsService lenderDetailsService;

    @Autowired
    public LenderSecurityCompServiceImpl(LenderSecurityRepo lenderSecurityRepo, LenderDetailsService lenderDetailsService) {
        this.lenderSecurityRepo = lenderSecurityRepo;
        this.lenderDetailsService = lenderDetailsService;
    }

    @Override
    public MethodResponse saveSecurityCompliance(SecurityComplianceRequest securityComplianceRequest) {
        try{
            LOGGER.info("Saving security compliance for lenderEmail={}", securityComplianceRequest.getLenderEmail());
            XlendzLender lender=lenderDetailsService.getLenderByEmail(securityComplianceRequest.getLenderEmail());
            FIleValidator.emptyFileCheck(securityComplianceRequest.getFile());
            LenderSecurityComplianceDocs securityComplianceDocs= buildSecurityCompObj(securityComplianceRequest, lender);
            lenderSecurityRepo.save(securityComplianceDocs);
            LOGGER.info("Security compliance saved successfully for lenderEmail={}", securityComplianceRequest.getLenderEmail());
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException | InvalidFileException | IOException e){
            throw new XlendzDataAccessException(e.getMessage());
        }
    }

    private static LenderSecurityComplianceDocs buildSecurityCompObj(SecurityComplianceRequest securityComplianceRequest, XlendzLender lender) throws IOException {
        LOGGER.info("Building LenderSecurityComplianceDocs object for lenderEmail={}",
                securityComplianceRequest.getLenderEmail());
        return LenderSecurityComplianceDocs.builder()
                .lender(lender)
                .digitalSecurityType(securityComplianceRequest.getDigitalSecurityType())
                .securityComplianceDocName(securityComplianceRequest.getFile().getOriginalFilename())
                .securityComplianceDocData(securityComplianceRequest.getFile().getBytes())
                .securityComplianceDocContentType(securityComplianceRequest.getFile().getContentType())
                .build();
    }
}
