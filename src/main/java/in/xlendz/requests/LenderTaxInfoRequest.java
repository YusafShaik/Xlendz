package in.xlendz.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderTaxInfoRequest {

    @NotBlank(message = "Lender email is required")
    private String lenderEmail;

    @NotNull(message = "Tax info file is required")
    private MultipartFile file;
}
