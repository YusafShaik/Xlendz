package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoanRequestProposalsDTO {

    private UserLoanRequestResponse userLoanRequestResponse;

    List<LenderLoanProposalResponse> lenderLoanProposalResponseList;

}
