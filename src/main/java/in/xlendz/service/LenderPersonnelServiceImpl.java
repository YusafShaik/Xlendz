package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.LenderKeyPersons;
import in.xlendz.entity.LenderSignatories;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.InvalidFileException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.LenderKeyPersonRepo;
import in.xlendz.repo.LendorSignatoriesRepo;
import in.xlendz.requests.LenderKeyPersonsRequest;
import in.xlendz.requests.LenderSignatoriesRequest;
import in.xlendz.util.FIleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class LenderPersonnelServiceImpl implements LenderPersonnelService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderPersonnelServiceImpl.class);
    private final LenderDetailsService lenderDetailsService;
    private final LendorSignatoriesRepo lendorSignatoriesRepo;
    private final LenderKeyPersonRepo lenderKeyPersonRepo;

    public LenderPersonnelServiceImpl( LenderDetailsService lenderDetailsService, LendorSignatoriesRepo lendorSignatoriesRepo, LenderKeyPersonRepo lenderKeyPersonRepo) {
        this.lenderDetailsService = lenderDetailsService;
        this.lendorSignatoriesRepo = lendorSignatoriesRepo;
        this.lenderKeyPersonRepo = lenderKeyPersonRepo;

    }

    @Override
    public MethodResponse saveLendorSignatories(LenderSignatoriesRequest lendersignatoriesRequest) {
        try {
            LOGGER.info("Saving signatory details for lenderEmail={}", lendersignatoriesRequest.getLenderEmail());
            XlendzLender lender = lenderDetailsService.getLenderByEmail(lendersignatoriesRequest.getLenderEmail());
            lendorSignatoriesRepo.save( buildSignatoriesObj(lendersignatoriesRequest, lender));
            LOGGER.info("Signatory details saved successfully for lenderEmail={}", lendersignatoriesRequest.getLenderEmail());
            return MethodResponse.SUCCESS;
        } catch (DataRetrievalException | InvalidFileException  e) {
            throw new XlendzDataAccessException(e.getMessage());
        }

    }

    private static LenderSignatories buildSignatoriesObj(LenderSignatoriesRequest lendersignatoriesRequest,
                                                XlendzLender lender) {
        LOGGER.info("Building LenderSignatories object for signatory details, lenderEmail={}", lendersignatoriesRequest.getLenderEmail());
        return LenderSignatories.builder()
                .xlendzLender(lender)
                .signatoryEmail(lendersignatoriesRequest.getSignatoryEmail())
                .signatoryPhone(lendersignatoriesRequest.getSignatoryPhone())
                .signatoryName(lendersignatoriesRequest.getSignatoryName())
                .signatoryRole(lendersignatoriesRequest.getSignatoryRole())
                .build();
    }



    @Override
    public MethodResponse saveKeyPersonnel(LenderKeyPersonsRequest lenderKeyPersonsRequest) {
        try{
            LOGGER.info("Saving key person details for lenderEmail={}", lenderKeyPersonsRequest.getLenderEmail());
            XlendzLender lender=lenderDetailsService.getLenderByEmail(lenderKeyPersonsRequest.getLenderEmail());
            FIleValidator.emptyFileCheck(lenderKeyPersonsRequest.getFile());
            LenderKeyPersons lenderKeyPerson= buildKeyPersonsObj(lenderKeyPersonsRequest, lender);
            lenderKeyPersonRepo.save(lenderKeyPerson);
            LOGGER.info("Key person details saved successfully for lenderEmail={}", lenderKeyPersonsRequest.getLenderEmail());
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException | InvalidFileException | IOException e){
           throw new XlendzDataAccessException(e.getMessage());
        }
    }

    private LenderKeyPersons buildKeyPersonsObj(LenderKeyPersonsRequest lenderKeyPersonsRequest,
                                                XlendzLender lender) throws IOException {
        LOGGER.info("Building LenderKeyPersons object for key person details, lenderEmail={}",
                lenderKeyPersonsRequest.getLenderEmail());
        return LenderKeyPersons.builder()
                .xlendzLender(lender)
                .keyPersonRole(lenderKeyPersonsRequest.getKeyPersonRole())
                .keyPersonName(lenderKeyPersonsRequest.getKeyPersonName())
                .keyPersonEmail(lenderKeyPersonsRequest.getKeyPersonEmail())
                .keyPersonPhone(lenderKeyPersonsRequest.getKeyPersonPhone())
                .fileData(lenderKeyPersonsRequest.getFile().getBytes())
                .fileName(lenderKeyPersonsRequest.getFile().getOriginalFilename())
                .fileContentTypes(lenderKeyPersonsRequest.getFile().getContentType())
                .build();
    }
}
