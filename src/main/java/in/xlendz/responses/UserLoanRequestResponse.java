package in.xlendz.responses;

import in.xlendz.constants.LoanRequestStatus;
import in.xlendz.constants.LoanTypesOffered;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoanRequestResponse {

    private LoanTypesOffered loanType;
    private Double loanAmount;
    private Date createdOn;
    private LoanRequestStatus loanRequestStatus;
}
