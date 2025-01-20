package in.xlendz.requests;

import in.xlendz.constants.LoanTypesOffered;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoanRequestDto {

    @NotNull(message = "Loan type is required")
    private LoanTypesOffered loanType;

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    private Double loanAmount;

    @NotNull(message = "User Id is required")
    private Long userId;


}
