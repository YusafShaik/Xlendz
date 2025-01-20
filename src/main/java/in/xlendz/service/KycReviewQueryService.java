package in.xlendz.service;

import in.xlendz.constants.KycStatus;
import in.xlendz.requests.KycReviewStatusUpdateRequest;
import in.xlendz.requests.UserDetailsFeedbackRequest;
import in.xlendz.responses.KycReviewListResponse;
import in.xlendz.responses.UserDetailsResponse;

import java.util.List;

public interface KycReviewQueryService {
    List<KycReviewListResponse> getKycReviewList();
    UserDetailsResponse getKycReviewByEmailId(String emailId);

}
