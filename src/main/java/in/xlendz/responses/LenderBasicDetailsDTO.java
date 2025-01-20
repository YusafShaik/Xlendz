package in.xlendz.responses;

import in.xlendz.constants.LenderBankingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderBasicDetailsDTO {

    private String companyName;

    private String registrationNumber;

    private LenderBankingType lenderBankingType;

    private Date dateOfInCorporation;
}
