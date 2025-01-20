package in.xlendz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lender_tax_info")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderTaxInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lenderTaxInfoId;
    @OneToOne
    private XlendzLender lender;
    private String taxInfoDocName;
    @Lob
    private byte[] taxInfoDocData;
    private String taxInfoDocContentType;
}
