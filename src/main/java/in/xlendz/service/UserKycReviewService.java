package in.xlendz.service;

import in.xlendz.responses.UserDetailsFeedbackResponse;

public interface UserKycReviewService {
    UserDetailsFeedbackResponse getUserKycReviewDetails(String xlendzUserId);
}
