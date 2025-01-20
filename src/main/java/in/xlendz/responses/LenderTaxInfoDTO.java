package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderTaxInfoDTO {

    private String taxInfoDocName;
    private String taxInfoDocContentType;
}
