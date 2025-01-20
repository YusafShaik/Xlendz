package in.xlendz.requests;

import in.xlendz.constants.DetailsStatus;
import in.xlendz.constants.LenderDetailsType;
import in.xlendz.constants.UserDetailsType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderKycCommentRequest {

    @NotNull(message = "Details Type is required")
    private LenderDetailsType detailsType;

    @NotBlank(message="Lender email can't be null or blank")
    private String lenderEmail;

    @NotNull(message = "Details Status is required")
    private DetailsStatus detailsStatus;

    @NotBlank(message = "Remarks is required")
    private String remarks;
}
