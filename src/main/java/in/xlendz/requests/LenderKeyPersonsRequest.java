package in.xlendz.requests;

import in.xlendz.constants.KeyPersonRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderKeyPersonsRequest {

    @NotBlank(message = "Lender email is required")
    private String lenderEmail;

    @NotBlank(message = "Key person name is required")
    private String keyPersonName;

    @NotBlank(message = "Key person phone is required")
    private String keyPersonPhone;

    @NotBlank(message = "Key person email is required")
    private String keyPersonEmail;

    @NotNull(message = "Key person role is required")
    private KeyPersonRoles keyPersonRole;

    @NotNull(message = "Key person file is required")
    private MultipartFile file;
}
