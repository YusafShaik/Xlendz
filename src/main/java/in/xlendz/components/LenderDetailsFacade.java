package in.xlendz.components;

import in.xlendz.entity.*;
import in.xlendz.repo.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Data
public class LenderDetailsFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(LenderDetailsFacade.class);
    private final LenderBasicDetailsRepo xlendzLenderBasicDetailsRepo;

    private final LenderRepo lenderRepo;
    private final LenderDocsRepo lenderDocsRepo;
    private final LendorSignatoriesRepo lendorSignatoriesRepo;
    private final LenderKeyPersonRepo lenderKeyPersonRepo;
    private final LenderProfileRepo lenderProfileRepo;
    private final LenderTaxInfoRepo lenderTaxInfoRepo;
    private final LenderSecurityRepo lenderSecurityRepo;
    private final LenderBankDetailsRepo lenderBankDetailsRepo;

    public LenderDetailsFacade(LenderBasicDetailsRepo xlendzLenderBasicDetailsRepo, LenderRepo lenderRepo, LenderDocsRepo lenderDocsRepo, LendorSignatoriesRepo lendorSignatoriesRepo, LenderKeyPersonRepo lenderKeyPersonRepo, LenderProfileRepo lenderProfileRepo, LenderTaxInfoRepo lenderTaxInfoRepo, LenderSecurityRepo lenderSecurityRepo, LenderBankDetailsRepo lenderBankDetailsRepo) {
        this.xlendzLenderBasicDetailsRepo = xlendzLenderBasicDetailsRepo;
        this.lenderRepo = lenderRepo;
        this.lenderDocsRepo = lenderDocsRepo;
        this.lendorSignatoriesRepo = lendorSignatoriesRepo;
        this.lenderKeyPersonRepo = lenderKeyPersonRepo;
        this.lenderProfileRepo = lenderProfileRepo;
        this.lenderTaxInfoRepo = lenderTaxInfoRepo;
        this.lenderSecurityRepo = lenderSecurityRepo;
        this.lenderBankDetailsRepo = lenderBankDetailsRepo;
    }
    public Optional<LenderBasicDetails> getLenderBasicDetails(XlendzLender xlendzLender) {
            LOGGER.info("Getting lender basic details, lenderEmail={}", xlendzLender.getEmail());
            return xlendzLenderBasicDetailsRepo.findByXlendzLender(xlendzLender);
    }

    public Optional<List<LenderDocuments>> getLenderDocuments(XlendzLender lender){
            LOGGER.info("Getting lender documents, lenderEmail={}", lender.getEmail());
           return lenderDocsRepo.findAllByXlendzLender(lender);
    }

    public Optional<List<LenderSignatories>> getLendorSignatories(XlendzLender lender){
            LOGGER.info("Getting lender signatories, lenderEmail={}", lender.getEmail());
            return lendorSignatoriesRepo.findAllByXlendzLender(lender);
    }

    public Optional<List<LenderKeyPersons>> getLenderKeyPersons(XlendzLender lender) {
        LOGGER.info("Getting lender key persons, lenderEmail={}", lender.getEmail());
        return lenderKeyPersonRepo.findAllByXlendzLender(lender);
    }

    public Optional<LenderProfile> getLenderProfile(XlendzLender lender){
            LOGGER.info("Getting lender profile, lenderEmail={}", lender.getEmail());
            return lenderProfileRepo.findByLender(lender);
    }

    public Optional<LenderTaxInfo> getLenderTaxInfo(XlendzLender lender){

            LOGGER.info("Getting lender tax info, lenderEmail={}", lender.getEmail());
            return lenderTaxInfoRepo.findByLender(lender);

    }

    public Optional<List<LenderSecurityComplianceDocs>> getLenderSecurityDocs(XlendzLender lender){
            LOGGER.info("Getting lender security, lenderEmail={}", lender.getEmail());
            return lenderSecurityRepo.findAllByLender(lender);
    }

    public Optional<LenderBankDetails> getLenderBankDetails(XlendzLender lender){
            LOGGER.info("Getting lender bank details, lenderEmail={}", lender.getEmail());
            return lenderBankDetailsRepo.findByLender(lender);
    }

    public Optional<XlendzLender> getLenderByEmail(String email) {
        LOGGER.info("Getting lender by email, email={}", email);
        return lenderRepo.findByEmail(email);
    }
}
