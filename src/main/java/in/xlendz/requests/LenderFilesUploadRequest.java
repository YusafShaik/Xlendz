package in.xlendz.requests;

import in.xlendz.constants.LenderDocumentTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderFilesUploadRequest {

    @NotBlank(message = "Lender email is required")
    private String lenderEmail;

    @NotNull(message = "File is required")
    private MultipartFile file;

    @NotNull(message = "Document type is required")
    private LenderDocumentTypes lenderDocumentType;
}
