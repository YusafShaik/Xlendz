package in.xlendz.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityDetailsRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long xlendzUserId;
    @NotBlank(message = "PAN number is required")
    private String panNumber;
    @NotBlank(message = "Aadhar number is required")
    private String aadharNumber;
}
