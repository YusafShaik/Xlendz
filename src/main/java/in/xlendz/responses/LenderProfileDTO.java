package in.xlendz.responses;

import in.xlendz.constants.LoanTypesOffered;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LenderProfileDTO {
    private String profileDocName;
    private String profileDocContentType;
    List<LoanTypesOffered> loanTypesOffered;

}
