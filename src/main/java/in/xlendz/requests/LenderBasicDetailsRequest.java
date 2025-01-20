package in.xlendz.requests;

import in.xlendz.constants.LenderBankingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderBasicDetailsRequest {

    @NotBlank(message = "Lender email is required")
    private String lenderEmail;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotNull(message = "LenderBankingType is required")
    private LenderBankingType lenderBankingType;

    @NotNull(message = "Date of incorporation is required")
    private Date dateOfInCorporation;
}
