package in.xlendz.service;

import in.xlendz.entity.XlendzUserKycReview;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.UserKycReviewRepo;
import in.xlendz.responses.UserDetailsFeedbackResponse;
import in.xlendz.responses.UserDetailsResponse;
import in.xlendz.responses.UserKycReviewResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserKycReviewServiceImpl implements UserKycReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserKycReviewServiceImpl.class);

    private final UserKycReviewRepo userKycReviewRepo;

    private final UserDetailsService  userDetailsService;

    private final ModelMapper modelMapper;

    public UserKycReviewServiceImpl(
            ModelMapper modelMapper, UserKycReviewRepo userKycReviewRepo, UserDetailsService userDetailsService) {
        this.userKycReviewRepo = userKycReviewRepo;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;

    }

    @Override
    public UserDetailsFeedbackResponse getUserKycReviewDetails(String xlendzUserId) {
        LOGGER.info("Fetching KYC review details for userId={}", xlendzUserId);
        try {
            UserDetailsResponse userDetails = userDetailsService.getUserDetails(xlendzUserId);
            List<XlendzUserKycReview> userKycReviews =
                    userKycReviewRepo.findByXlendzUser_XlendzUserIdAndIsLatest(Long.valueOf(xlendzUserId),true);
            List<UserKycReviewResponse> userKycReviewResponses = userKycReviews.stream().map(kycReview ->
                    modelMapper.map(kycReview, UserKycReviewResponse.class)).toList();
            return UserDetailsFeedbackResponse.builder()
                    .userDetailsResponse(userDetails)
                    .xlendzUserKycReviewResponses(userKycReviewResponses)
                    .build();
        } catch (DataRetrievalException e) {
            throw new XlendzDataAccessException("Error while fetching user KYC review details", e);
        }
    }

}
