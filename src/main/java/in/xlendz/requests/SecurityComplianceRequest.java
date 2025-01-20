package in.xlendz.requests;

import in.xlendz.constants.DigitalSecurityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityComplianceRequest {

    @NotBlank(message = "Lender email is required")
    private String lenderEmail;
    @NotNull(message = "Security compliance file is required")
    private MultipartFile file;
    @NotNull(message = "Digital security type is required")
    private DigitalSecurityType digitalSecurityType;

}
