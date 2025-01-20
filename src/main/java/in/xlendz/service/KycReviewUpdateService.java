package in.xlendz.service;

import in.xlendz.constants.KycStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.KycReviewStatusUpdateRequest;
import in.xlendz.requests.UserDetailsFeedbackRequest;

public interface KycReviewUpdateService {

    MethodResponse userDetailsReview(UserDetailsFeedbackRequest userDetailsFeedbackRequest);
    KycStatus updateKycStatus(KycReviewStatusUpdateRequest kycReviewStatusUpdateRequest);
    MethodResponse approveKyc(String userId);
}
