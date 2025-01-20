package in.xlendz.requests;

import in.xlendz.constants.KycStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KycReviewStatusUpdateRequest {

    @NotBlank
    private String xlendzUserId;

    @NotBlank
    private KycStatus  kycStatus;
}
