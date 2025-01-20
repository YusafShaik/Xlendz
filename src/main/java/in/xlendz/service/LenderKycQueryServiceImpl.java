package in.xlendz.service;

import in.xlendz.components.LenderDetailsFacade;
import in.xlendz.constants.KycStatus;
import in.xlendz.entity.LenderBasicDetails;
import in.xlendz.entity.LenderKycReview;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.LenderKycReviewRepo;
import in.xlendz.responses.KycReviewListResponse;
import in.xlendz.responses.LenderKycDetailsComments;
import in.xlendz.responses.LenderKycReviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LenderKycQueryServiceImpl implements LenderKycQueryService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderKycQueryServiceImpl.class);
    private final LenderDetailsFacade lenderDetailsFacade;
    private final LenderKycReviewRepo lenderKycReviewRepo;
    private final LenderDetailsService lenderDetailsService;

    @Autowired
    public LenderKycQueryServiceImpl(LenderDetailsFacade lenderDetailsFacade, LenderKycReviewRepo lenderKycReviewRepo
            , LenderDetailsService lenderDetailsService) {
        this.lenderDetailsFacade = lenderDetailsFacade;
        this.lenderKycReviewRepo = lenderKycReviewRepo;
        this.lenderDetailsService = lenderDetailsService;
    }


    @Override
    public List<KycReviewListResponse> getLenderKycList() {
        LOGGER.info("Fetching KYC review list");
        List<LenderBasicDetails> lenderBasicDetails =
                lenderDetailsFacade.getXlendzLenderBasicDetailsRepo()
                        .findByXlendzLender_KycStatus(KycStatus.PENDING);

        LOGGER.info("Fetched {} pending KYC reviews", lenderBasicDetails.size());
        return lenderBasicDetails.stream()
                .map(this::mapToKycReviewListResponse)
                .toList();
    }

    @Override
    public LenderKycDetailsComments getLenderKycRemarks(String lenderEmail) {
        try {
            XlendzLender xlendzLender = lenderDetailsService.getLenderByEmail(lenderEmail);
            LOGGER.info("Fetching lender kyc remarks for lenderEmail={}", lenderEmail);
            List<LenderKycReview> lenderKycReviews =
                    lenderKycReviewRepo.findByXlendzLender_LenderIdAndIsLatest(xlendzLender.getLenderId(), true);
            LOGGER.info("Fetched lender kyc remarks for lenderEmail={}", lenderEmail);
            return LenderKycDetailsComments.builder()
                    .kycReviewResponses(getKycReviewList(lenderKycReviews))
                    .lenderDetailsDTO(lenderDetailsService.getLenderDetails(lenderEmail))
                    .build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(e.getMessage(), e);
        } catch (Exception e) {
            throw new DataUpdationException(e.getMessage(), e);
        }
    }

    private static List<LenderKycReviewResponse> getKycReviewList(List<LenderKycReview> lenderKycReviews) {
        LOGGER.debug("Mapping LenderKycReview to LenderKycReviewResponse");
        return lenderKycReviews.stream()
                .map(lenderKycReview -> LenderKycReviewResponse.builder()
                        .detailsType(lenderKycReview.getDetailsType())
                        .isLatest(lenderKycReview.isLatest())
                        .remarks(lenderKycReview.getRemarks())
                        .detailsStatus(lenderKycReview.getDetailsStatus())
                        .build())
                .toList();
    }

    private KycReviewListResponse mapToKycReviewListResponse(LenderBasicDetails lenderBasicDetail) {
        LOGGER.debug("Mapping LenderBasicDetails to KycReviewListResponse for company: {}",
                lenderBasicDetail.getCompanyName());
        return KycReviewListResponse.builder()
                .fullName(lenderBasicDetail.getCompanyName())
                .emailId(Optional.ofNullable(lenderBasicDetail.getXlendzLender())
                        .map(XlendzLender::getEmail)
                        .orElse("N/A"))
                .build();
    }

}
