package in.xlendz.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LenderBankDetailsRequest {
    @NotBlank(message = "Lender email is required")
    private String lenderEmail;
    @NotBlank(message = "Bank name is required")
    private String bankName;
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    @NotBlank(message = "IFSC code is required")
    private String ifscCode;
}
