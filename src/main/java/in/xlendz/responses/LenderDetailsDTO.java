package in.xlendz.responses;

import in.xlendz.constants.KycStatus;
import in.xlendz.entity.LenderKeyPersons;
import in.xlendz.entity.LenderSignatories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LenderDetailsDTO {
    private KycStatus kycStatus;
    private boolean isVerified;
    private LenderBasicDetailsDTO lenderBasicDetailsDTO;
    private List<LenderDocumentsDTO> lenderDocumentsDTO;
    private List<LenderSignatoriesDTO> lenderSignatoriesDTO;
    private List<LenderKeyPersonsDTO> lenderKeyPersons;
    private LenderProfileDTO lenderProfile;
    private LenderTaxInfoDTO lenderTaxInfo;
    private List<LenderSecurityComplianceDocsDTO> lenderSecurityComplianceDocs;
    private LenderBankDetailsDTO lenderBankDetails;

}
