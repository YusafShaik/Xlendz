package in.xlendz.responses;

import in.xlendz.constants.DigitalSecurityType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderSecurityComplianceDocsDTO {

    private DigitalSecurityType digitalSecurityType;
    private String securityComplianceDocName;
    private String securityComplianceDocContentType;
}
