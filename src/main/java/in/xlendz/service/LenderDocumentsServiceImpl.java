package in.xlendz.service;

import in.xlendz.constants.LenderDocumentTypes;
import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.*;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.InvalidFileException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.*;
import in.xlendz.requests.*;
import in.xlendz.util.FIleValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class LenderDocumentsServiceImpl implements LenderDocumentsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderDocumentsServiceImpl.class);
    private final LenderDocsRepo lenderDocsRepo;

    private final LenderDetailsService lenderDetailsService;

    private final LenderProfileRepo lenderProfileRepo;

    private final LenderTaxInfoRepo lenderTaxInfoRepo;

    @Autowired
    public LenderDocumentsServiceImpl(LenderDocsRepo lenderDocsRepo, LenderDetailsService lenderDetailsService,
                                      LenderProfileRepo lenderProfileRepo,
                                      LenderTaxInfoRepo lenderTaxInfoRepo) {
        this.lenderDocsRepo = lenderDocsRepo;
        this.lenderDetailsService = lenderDetailsService;
        this.lenderProfileRepo = lenderProfileRepo;
        this.lenderTaxInfoRepo = lenderTaxInfoRepo;
    }

    @Override
    @Transactional
    public MethodResponse saveLenderRegistrationDocs(LenderFilesUploadRequest lenderFilesUploadRequest) {
        try {
            LOGGER.info("Received request to save lender registration docs, lenderFilesUploadRequest={}", lenderFilesUploadRequest.getLenderEmail());
            XlendzLender lender= lenderDetailsService.getLenderByEmail(lenderFilesUploadRequest.getLenderEmail());
            FIleValidator.emptyFileCheck(lenderFilesUploadRequest.getFile());
            Optional<LenderDocuments> lenderDoc=getLenderDoc(lenderFilesUploadRequest.getLenderEmail(),
                    lenderFilesUploadRequest.getLenderDocumentType());
            lenderDoc.ifPresent(lenderDocsRepo::delete);
            LenderDocuments lenderDocuments= buildLenderDocObj(lenderFilesUploadRequest, lender);
            lenderDocsRepo.save(lenderDocuments);
            LOGGER.info("Lender registration docs saved successfully, lenderFilesUploadRequest={}", lenderFilesUploadRequest.getLenderEmail());
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException | InvalidFileException | IOException e){
            throw  new XlendzDataAccessException(e.getMessage());
        }
    }

    private  LenderDocuments buildLenderDocObj(LenderFilesUploadRequest lenderFilesUploadRequest, XlendzLender lender) throws IOException {
       LOGGER.info("Building LenderDocuments object for lenderEmail={}", lenderFilesUploadRequest.getLenderEmail());
        return LenderDocuments.builder()
                .xlendzLender(lender)
                .lenderDocumentType(lenderFilesUploadRequest.getLenderDocumentType())
                .fileData(lenderFilesUploadRequest.getFile().getBytes())
                .fileContentTypes(lenderFilesUploadRequest.getFile().getContentType())
                .fileName(lenderFilesUploadRequest.getFile().getOriginalFilename())
                .build();
    }

    @Override
    public MethodResponse saveCompanyProfile(CompanyProfileRequest companyProfileRequest) {
        try{
            LOGGER.info("Saving company profile for lenderEmail={}", companyProfileRequest.getLenderEmail());
            XlendzLender lender=lenderDetailsService.getLenderByEmail(companyProfileRequest.getLenderEmail());
            FIleValidator.emptyFileCheck(companyProfileRequest.getFile());
            LenderProfile lenderProfile= buildLenderProfileObj(companyProfileRequest, lender);
            lenderProfileRepo.save(lenderProfile);
            LOGGER.info("Company profile saved successfully for lenderEmail={}", companyProfileRequest.getLenderEmail());
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException | InvalidFileException | IOException e){
            throw  new XlendzDataAccessException(e.getMessage());
        }
    }

    private  LenderProfile buildLenderProfileObj(CompanyProfileRequest companyProfileRequest, XlendzLender lender) throws IOException {
        LOGGER.info("Building LenderProfile object for lenderEmail={}", companyProfileRequest.getLenderEmail());
        return LenderProfile.builder()
                .lender(lender)
                .loanTypesOffered(companyProfileRequest.getLoanTypesOffered())
                .profileDocName(companyProfileRequest.getFile().getOriginalFilename())
                .profileDocData(companyProfileRequest.getFile().getBytes())
                .profileDocContentType(companyProfileRequest.getFile().getContentType())
                .build();
    }

    @Override
    public MethodResponse saveLenderTaxInfo(LenderTaxInfoRequest lenderTaxInfoRequest) {
       try{
           XlendzLender lender=lenderDetailsService.getLenderByEmail(lenderTaxInfoRequest.getLenderEmail());
           FIleValidator.emptyFileCheck(lenderTaxInfoRequest.getFile());
           LenderTaxInfo lenderTaxInfo= getBuild(lenderTaxInfoRequest, lender);
           lenderTaxInfoRepo.save(lenderTaxInfo);
           LOGGER.info("Lender tax info saved successfully for lenderEmail={}", lenderTaxInfoRequest.getLenderEmail());
           return MethodResponse.SUCCESS;
       }catch (DataRetrievalException | InvalidFileException | IOException e){
          throw  new XlendzDataAccessException(e.getMessage());
       }
    }

    private  LenderTaxInfo getBuild(LenderTaxInfoRequest lenderTaxInfoRequest, XlendzLender lender) throws IOException {
        return LenderTaxInfo.builder()
                .lender(lender)
                .taxInfoDocName(lenderTaxInfoRequest.getFile().getOriginalFilename())
                .taxInfoDocData(lenderTaxInfoRequest.getFile().getBytes())
                .taxInfoDocContentType(lenderTaxInfoRequest.getFile().getContentType())
                .build();
    }


    public Optional<LenderDocuments> getLenderDoc(String lenderEmail, LenderDocumentTypes lenderDocumentTypes) {
        LOGGER.info("Received request to get lender doc, lenderEmail={}", lenderEmail);
        return lenderDocsRepo.findByLenderDocumentTypeAndXlendzLender_Email(lenderDocumentTypes, lenderEmail);
    }
}
