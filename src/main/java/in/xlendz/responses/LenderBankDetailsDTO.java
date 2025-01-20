package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderBankDetailsDTO {
    private String bankName;
    private String accountNumber;
    private String ifscCode;
}
