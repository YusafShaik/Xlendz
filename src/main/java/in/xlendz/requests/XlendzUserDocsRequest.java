package in.xlendz.requests;

import in.xlendz.constants.FileUploadTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserDocsRequest {

    private Long xlendzUserId;

    private MultipartFile  file;

    private FileUploadTypes  fileUploadType;
}
