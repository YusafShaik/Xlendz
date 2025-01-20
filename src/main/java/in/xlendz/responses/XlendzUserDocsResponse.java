package in.xlendz.responses;

import in.xlendz.constants.FileUploadTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserDocsResponse {
    private String fileName;
    private FileUploadTypes fileUploadType;
}
