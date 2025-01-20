package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LenderKycDetailsComments {

    private LenderDetailsDTO lenderDetailsDTO;
    private List<LenderKycReviewResponse> kycReviewResponses;

}
