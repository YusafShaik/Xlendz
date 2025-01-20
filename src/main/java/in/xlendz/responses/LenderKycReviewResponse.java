package in.xlendz.responses;

import in.xlendz.constants.DetailsStatus;
import in.xlendz.constants.LenderDetailsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LenderKycReviewResponse {

    private LenderDetailsType detailsType;

    private String remarks;

    private DetailsStatus detailsStatus;

    private boolean isLatest;

}
