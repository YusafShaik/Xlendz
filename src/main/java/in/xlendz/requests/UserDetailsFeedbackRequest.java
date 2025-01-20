package in.xlendz.requests;

import in.xlendz.constants.DetailsStatus;
import in.xlendz.constants.UserDetailsType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsFeedbackRequest {

    @NotNull(message = "Details Type is required")
    private UserDetailsType detailsType;

    @NotBlank(message="user id cant be null or blank")
    private String userId;

    @NotNull(message = "Details Status is required")
    private DetailsStatus detailsStatus;

    @NotBlank(message = "Remarks is required")
    private String remarks;
}
