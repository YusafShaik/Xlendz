package in.xlendz.requests;

import in.xlendz.constants.SignatoryRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderSignatoriesRequest {
    @NotBlank(message = "Lender email is required")
    private String lenderEmail;
    @NotBlank(message = "Signatory email is required")
    private String signatoryEmail;
    @NotBlank(message = "Signatory name is required")
    private String signatoryName;
    @NotBlank(message = "Signatory phone is required")
    private String signatoryPhone;
    @NotNull(message = "Signatory role is required")
    private SignatoryRoles signatoryRole;

}
