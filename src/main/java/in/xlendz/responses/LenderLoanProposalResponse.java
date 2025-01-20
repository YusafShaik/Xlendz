package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LenderLoanProposalResponse {

    private String lenderName;
    private double rateOfInterest;
    private double processingFees;
    private int tenure;
    private double loanAmount;
}
