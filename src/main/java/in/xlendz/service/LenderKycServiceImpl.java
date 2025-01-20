package in.xlendz.service;

import in.xlendz.components.LenderDetailsFacade;
import in.xlendz.constants.*;
import in.xlendz.entity.LenderDocuments;
import in.xlendz.entity.LenderKycReview;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.LenderKycReviewRepo;
import in.xlendz.requests.LenderKycCommentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class LenderKycServiceImpl implements LenderKycUpdateService {

    private static final Logger LOGGER= LoggerFactory.getLogger(LenderKycServiceImpl.class);
    private final LenderDetailsFacade lenderDetailsFacade;
    private final LenderDetailsService lenderDetailsService;
    private final LenderKycReviewRepo lenderKycReviewRepo;

    @Autowired
    public LenderKycServiceImpl(LenderDetailsFacade lenderDetailsFacade, LenderDetailsService lenderDetailsService, LenderKycReviewRepo lenderKycReviewRepo) {
        this.lenderDetailsFacade = lenderDetailsFacade;
        this.lenderDetailsService = lenderDetailsService;
        this.lenderKycReviewRepo = lenderKycReviewRepo;
    }
    @Override
    public MethodResponse submitLenderKyc(String lenderEmail) {
        try {
            LOGGER.info("Submitting lender kyc for lenderEmail={}", lenderEmail);
            XlendzLender lender=lenderDetailsService.getLenderByEmail(lenderEmail);
            if (!allDetailsPresent(lender)) {
                return MethodResponse.PLEASE_UPDATE_ALL_DETAILS;
            }
            lender.setKycStatus(KycStatus.PENDING);
            lenderDetailsFacade.getLenderRepo().save(lender);
            LOGGER.info("Lender kyc submitted successfully for lenderEmail={}", lenderEmail);
            return MethodResponse.SUCCESS;
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(ExceptionConstants.ERROR_WHILE_FETCHING_LENDER_DETAILS, e);
        } catch (DataUpdationException e) {
            throw new DataUpdationException(ExceptionConstants.ERROR_WHILE_UPDATING_LENDER_DETAILS, e);
        }
    }

    @Override
    public MethodResponse lenderKycComments(LenderKycCommentRequest lenderKycCommentRequest) {
        try{
            LOGGER.info("Updating lender kyc comments for lenderEmail={}", lenderKycCommentRequest.getLenderEmail());
            XlendzLender xlendzLender= lenderDetailsService.getLenderByEmail(lenderKycCommentRequest.getLenderEmail());
            lenderKycReviewRepo.save( buildLenderKycObj(lenderKycCommentRequest, xlendzLender));
            LOGGER.info("Lender kyc comments updated successfully for lenderEmail={}", lenderKycCommentRequest.getLenderEmail());
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException e){
            throw new DataRetrievalException(e.getMessage(), e);
        }catch (Exception e){
            throw new DataUpdationException(e.getMessage(), e);
        }
    }

    @Override
    public MethodResponse lenderKycApprove(String lenderEmail) {
       try{
           XlendzLender lender=lenderDetailsService.getLenderByEmail(lenderEmail);
           EnumSet<LenderDetailsType> expectedDetailsTypes = EnumSet.of(
                   LenderDetailsType.BASIC_DETAILS,
                   LenderDetailsType.COMPANY_PROFILE,
                   LenderDetailsType.TAX_INFO,
                   LenderDetailsType.SECURITY_COMPLIANCE_DOCS,
                   LenderDetailsType.BANK_DETAILS,
                   LenderDetailsType.SIGNATORIES,
                   LenderDetailsType.KEY_PERSONS,
                   LenderDetailsType.DOCUMENTS
           );
           List<LenderKycReview> lenderKycReviews = lenderKycReviewRepo.findByXlendzLender_LenderIdAndIsLatest(lender.getLenderId(), true);
           for (LenderKycReview review : lenderKycReviews) {
               if (expectedDetailsTypes.contains(review.getDetailsType()) && review.getDetailsStatus() == DetailsStatus.ACCEPTED) {
                   expectedDetailsTypes.remove(review.getDetailsType());
               }
           }
           if(expectedDetailsTypes.isEmpty()){
               lender.setKycStatus(KycStatus.VERIFIED);
              lenderDetailsFacade.getLenderRepo().save(lender);
               return MethodResponse.SUCCESS;
           }
           return MethodResponse.PLEASE_REVIEW_ALL_DETAILS;
       }catch (DataRetrievalException e){
           throw new DataRetrievalException(e.getMessage(), e);
       }catch (Exception e){
           throw new DataUpdationException(e.getMessage(), e);
       }
    }

    @Override
    public MethodResponse updateLenderKycStatus(String lenderEmail, KycStatus kycStatus) {
        try{
            LOGGER.info("Updating lender kyc status for lenderEmail={}, kycStatus={}", lenderEmail, kycStatus);
            XlendzLender lender=lenderDetailsService.getLenderByEmail(lenderEmail);
            lender.setKycStatus(kycStatus);
            lenderDetailsFacade.getLenderRepo().save(lender);
            LOGGER.info("Lender kyc status updated successfully for lenderEmail={}, kycStatus={}", lenderEmail, kycStatus);
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException e){
            throw new DataRetrievalException(e.getMessage(), e);
        }catch (Exception e){
            throw new DataUpdationException(e.getMessage(), e);
        }
    }

    private LenderKycReview buildLenderKycObj(LenderKycCommentRequest lenderKycCommentRequest,
                                              XlendzLender xlendzLender) {
        LOGGER.info("Building lender kyc object for lenderEmail={}", lenderKycCommentRequest.getLenderEmail());
        return LenderKycReview.builder()
                .detailsType(lenderKycCommentRequest.getDetailsType())
                .xlendzLender(xlendzLender)
                .remarks(lenderKycCommentRequest.getRemarks())
                .detailsStatus(lenderKycCommentRequest.getDetailsStatus())
                .isLatest(true)
                .build();
    }

    public  boolean allDetailsPresent(XlendzLender lender) {
        LOGGER.info("Checking if all details are present for lender, lenderEmail={}", lender.getEmail());
        return isDetailPresent(LenderDetailsType.BASIC_DETAILS, lenderDetailsFacade.getLenderBasicDetails(lender)) &&
                isAllDocsDetailPresent( lenderDetailsFacade.getLenderDocuments(lender)) &&
                isDetailPresent(LenderDetailsType.SIGNATORIES, lenderDetailsFacade.getLendorSignatories(lender)) &&
                isDetailPresent(LenderDetailsType.KEY_PERSONS, lenderDetailsFacade.getLenderKeyPersons(lender)) &&
                isDetailPresent(LenderDetailsType.COMPANY_PROFILE, lenderDetailsFacade.getLenderProfile(lender)) &&
                isDetailPresent(LenderDetailsType.TAX_INFO, lenderDetailsFacade.getLenderTaxInfo(lender)) &&
                isDetailPresent(LenderDetailsType.SECURITY_COMPLIANCE_DOCS, lenderDetailsFacade.getLenderSecurityDocs(lender)) &&
                isDetailPresent(LenderDetailsType.BANK_DETAILS, lenderDetailsFacade.getLenderBankDetails(lender));
    }

    private boolean isAllDocsDetailPresent(Optional<List<LenderDocuments>> lenderDocuments) {
        LOGGER.info("Checking if all documents are present.");
        if (lenderDocuments.isPresent()) {
            return lenderDocuments.get().size() >= LenderDocumentTypes.values().length;
        }
        LOGGER.info("{} not found, please update all details.", LenderDetailsType.DOCUMENTS);
        return false;
    }

    private <T> boolean isDetailPresent(LenderDetailsType detailName, Optional<T> detail) {
        if (detail.isEmpty()) {
            LOGGER.info("{} not found, please update all details.", detailName);
            return false;
        }
        LOGGER.info("{} found.", detailName);
        return true;
    }

}
