package in.xlendz.requests;

import in.xlendz.constants.ProposalStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanProposalDTO {

    @NotNull(message = "Loan RequestId cant be null or empty")
    private Long loanRequestId;

    @NotNull(message = "Lender email cant be null or empty")
    private String lenderEmail;

    @Positive
    @NotNull(message = "Rate of interest cant be null or empty")
    private double rateOfInterest;

    @NotNull(message = "Processing fees cant be null or empty")
    private double processingFees;

    @Min(value = 3, message = "Minimum tenure is 3 months")
    @Max(value = 60, message = "Maximum tenure is 60 months")
    private int tenure;

    @NotNull(message = "Loan amount cant be null or empty")
    private double loanAmount;

}
