package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.KycStatus;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserBasicDetails;
import in.xlendz.entity.XlendzUserKycReview;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.UserBasicDetailsRepo;
import in.xlendz.repo.UserKycReviewRepo;
import in.xlendz.repo.UserRepo;
import in.xlendz.requests.KycReviewStatusUpdateRequest;
import in.xlendz.requests.UserDetailsFeedbackRequest;
import in.xlendz.responses.KycReviewListResponse;
import in.xlendz.responses.UserDetailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KycReviewQueryServiceImpl implements KycReviewQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KycReviewQueryServiceImpl.class);
    private final UserBasicDetailsRepo xlendzUserBasicDetailsRepo;
    private final UserDetailsService userDetailsService;

    @Autowired
    public KycReviewQueryServiceImpl(UserRepo xlendzUserRepo, UserBasicDetailsRepo xlendzUserBasicDetailsRepo,
                                     UserDetailsService userDetailsService, UserKycReviewRepo xlendzUserKycReviewRepo) {
        this.xlendzUserBasicDetailsRepo = xlendzUserBasicDetailsRepo;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public List<KycReviewListResponse> getKycReviewList() {
        try {
            LOGGER.info("Fetching KYC review list");
            List<XlendzUserBasicDetails> xlendzUserBasicDetails =
                    xlendzUserBasicDetailsRepo.findByXlendzUserId_KycStatus(KycStatus.PENDING);
            if(xlendzUserBasicDetails.isEmpty()){
                LOGGER.info("No pending KYC review found");
            }
            return xlendzUserBasicDetails.stream()
                    .map(xlendzUserBasicDetail -> KycReviewListResponse.builder()
                            .fullName(xlendzUserBasicDetail.getFirstName() + " " + xlendzUserBasicDetail.getLastName())
                            .emailId(xlendzUserBasicDetail.getXlendzUserId().getEmail())
                            .build()).toList();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(e.getMessage());
        } catch (Exception e) {
            throw new XlendzDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public UserDetailsResponse getKycReviewByEmailId(String emailId) {
        try {
            LOGGER.info("Fetching KYC review details for emailId: {}", emailId);
            return userDetailsService.getUserDetails(emailId);
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(ExceptionConstants.SOME_INTERNAL_ERROR_IS_OCCURRED);
        } catch (Exception e) {
            throw new XlendzDataAccessException(e.getMessage(), e);
        }
    }


}
