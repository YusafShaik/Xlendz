package in.xlendz.requests;

import in.xlendz.constants.LoanTypesOffered;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileRequest {

    @NotBlank(message = "Lender email is required")
    private String lenderEmail;
    @NotNull(message = "Loan types offered is required")
    List<LoanTypesOffered> loanTypesOffered;
    @NotNull(message = "Profile file is required")
    private MultipartFile file;

}
