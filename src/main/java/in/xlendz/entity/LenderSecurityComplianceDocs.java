package in.xlendz.entity;

import in.xlendz.constants.DigitalSecurityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "lender_security_compliance_docs")
@Builder
@NoArgsConstructor
public class LenderSecurityComplianceDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lenderSecurityComplianceDocsId;
    @OneToOne
    private XlendzLender lender;
    @Enumerated(EnumType.STRING)
    private DigitalSecurityType digitalSecurityType;
    private String securityComplianceDocName;
    private byte[] securityComplianceDocData;
    private String securityComplianceDocContentType;
}
