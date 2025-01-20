package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDetailsFeedbackResponse {

    UserDetailsResponse userDetailsResponse;

    List<UserKycReviewResponse>  xlendzUserKycReviewResponses;

}
