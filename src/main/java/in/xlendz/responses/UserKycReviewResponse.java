package in.xlendz.responses;

import in.xlendz.constants.DetailsStatus;
import in.xlendz.constants.UserDetailsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserKycReviewResponse {

    private UserDetailsType detailsType;

    private String remarks;

    private DetailsStatus detailsStatus;

    private boolean isLatest;
}
