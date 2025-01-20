package in.xlendz.service;

import in.xlendz.constants.*;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserKycReview;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.UserKycReviewRepo;
import in.xlendz.repo.UserRepo;
import in.xlendz.requests.KycReviewStatusUpdateRequest;
import in.xlendz.requests.UserDetailsFeedbackRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class KycReviewUpdateServiceImpl implements KycReviewUpdateService{

    private static final Logger LOGGER= LoggerFactory.getLogger(KycReviewUpdateServiceImpl.class);
    private final UserKycReviewRepo xlendzUserKycReviewRepo;
    private final UserRepo xlendzUserRepo;

    @Autowired
    public KycReviewUpdateServiceImpl(UserKycReviewRepo xlendzUserKycReviewRepo, UserRepo xlendzUserRepo) {
        this.xlendzUserKycReviewRepo = xlendzUserKycReviewRepo;
        this.xlendzUserRepo = xlendzUserRepo;
    }

    @Override
    @Transactional
    public MethodResponse userDetailsReview(UserDetailsFeedbackRequest userDetailsFeedbackRequest) {
        try {
            LOGGER.info("Updating user details review for userId: {}", userDetailsFeedbackRequest.getUserId());
            updateOldReviewStatus(userDetailsFeedbackRequest.getDetailsType(), userDetailsFeedbackRequest.getUserId());
            LOGGER.info("Saving user details review for userId: {}", userDetailsFeedbackRequest.getUserId());
            XlendzUser xlendzUser = getByXlendzUserId(userDetailsFeedbackRequest.getUserId());
            xlendzUserKycReviewRepo.save(XlendzUserKycReview.builder()
                    .detailsStatus(userDetailsFeedbackRequest.getDetailsStatus())
                    .remarks(userDetailsFeedbackRequest.getRemarks())
                    .detailsType(userDetailsFeedbackRequest.getDetailsType())
                    .isLatest(true)
                    .xlendzUser(xlendzUser)
                    .build());
            LOGGER.info("User details review saved successfully for userId: {}", userDetailsFeedbackRequest.getUserId());
            return MethodResponse.SUCCESS;
        }catch (DataUpdationException e){
           throw new DataUpdationException(e.getMessage(), e);
        }
    }

    private XlendzUser getByXlendzUserId(String userId) {
        try{
            LOGGER.info("Fetching user Details by userId: {}", userId);
            if (userId == null) {
                throw new DataRetrievalException(ExceptionConstants.USER_ID_NOT_PROVIDED, new NullPointerException());
            }
            Optional<XlendzUser> xlendzUser=xlendzUserRepo.findByXlendzUserId(Long.valueOf(userId));
            if(xlendzUser.isPresent()){
                return xlendzUser.get();
            }
            throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND+userId);
        }catch (DataRetrievalException e){
            throw new DataRetrievalException(e.getMessage());
        }catch (Exception e){
            throw new XlendzDataAccessException(e.getMessage(), e);
        }

    }

    @Override
    public KycStatus updateKycStatus(KycReviewStatusUpdateRequest kycReviewStatusUpdateRequest) {
        try {
            LOGGER.info("Updating KYC status for userId: {}", kycReviewStatusUpdateRequest.getXlendzUserId());
            XlendzUser xlendzUser = getByXlendzUserId(kycReviewStatusUpdateRequest.getXlendzUserId());
            if(xlendzUser.getKycStatus()==KycStatus.VERIFIED){
                return KycStatus.VERIFIED;
            }
            KycStatus newKycStatus = kycReviewStatusUpdateRequest.getKycStatus();
            if (newKycStatus == null) {
                throw new DataUpdationException(ExceptionConstants.KYC_STATUS_NOT_PROVIDED, new NullPointerException());
            }
            xlendzUser.setKycStatus(kycReviewStatusUpdateRequest.getKycStatus());
            xlendzUserRepo.save(xlendzUser);
            LOGGER.info("KYC status updated successfully for userId: {}", kycReviewStatusUpdateRequest.getXlendzUserId());
            return newKycStatus;
        } catch (DataUpdationException e) {
            throw new DataUpdationException(e.getMessage(),e);
        } catch (Exception e) {
            throw new XlendzDataAccessException(e.getMessage(), e);
        }

    }

    @Override
    public MethodResponse approveKyc(String userId) {
        try{
            XlendzUser xlendzUser=getByXlendzUserId(userId);
            List<XlendzUserKycReview> xlendzUserKycReviews=xlendzUserKycReviewRepo.findByXlendzUser(xlendzUser);
            EnumSet<UserDetailsType> expectedDetailsTypes = EnumSet.of(
                    UserDetailsType.BASIC_DETAILS,
                    UserDetailsType.IDENTITY_DETAILS,
                    UserDetailsType.EMPLOYMENT_DETAILS,
                    UserDetailsType.DOCUMENTS
            );
            for (XlendzUserKycReview review : xlendzUserKycReviews) {
                if (expectedDetailsTypes.contains(review.getDetailsType()) && review.getDetailsStatus() == DetailsStatus.ACCEPTED) {
                    expectedDetailsTypes.remove(review.getDetailsType());
                }
            }
            if(expectedDetailsTypes.isEmpty()){
                xlendzUser.setKycStatus(KycStatus.VERIFIED);
                xlendzUserRepo.save(xlendzUser);
                return MethodResponse.SUCCESS;
            }
            return MethodResponse.PLEASE_REVIEW_ALL_DETAILS;

        }catch(DataRetrievalException  e){
            throw new DataRetrievalException(e.getMessage(), e);
        } catch (Exception e){
            throw new XlendzDataAccessException(e.getMessage(), e);
        }
    }

    private void updateOldReviewStatus(UserDetailsType detailsType, String userId) {
        try {
            LOGGER.info("Updating old review status for userId: {}", userId);
            List<XlendzUserKycReview> oldReviews =
                    xlendzUserKycReviewRepo.findByXlendzUserAndIsLatestAndDetailsType(getByXlendzUserId(userId),
                            true,detailsType);
            oldReviews.forEach(review -> review.setLatest(false));
            LOGGER.info("Old review status updated successfully for userId: {}", userId);
        } catch (DataUpdationException e) {
            throw new DataUpdationException(e.getMessage(), e);
        } catch (Exception e) {
            throw new XlendzDataAccessException(e.getMessage(), e);
        }
    }
}
