package in.xlendz.responses;

import in.xlendz.constants.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse {
    private Long userId;
    private XlendzUserBasicDetailsResponse userBasicDetails;
    private UserIdentityDetailsResponse userIdentityDetails;
    private UserEmploymentDetailsResponse userEmploymentDetails;
    private boolean isVerified;
    private KycStatus kycStatus;
    List<XlendzUserDocsResponse> userDocs;
}
