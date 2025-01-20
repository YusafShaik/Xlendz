package in.xlendz.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanProposalUpdateDTO {

    private long loanProposalId;

    private double rateOfInterest;

    private double processingFees;

    private int tenure;

    private double loanAmount;
}
