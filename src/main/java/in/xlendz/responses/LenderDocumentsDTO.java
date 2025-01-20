package in.xlendz.responses;

import in.xlendz.constants.LenderDocumentTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderDocumentsDTO {

    private  String fileName;

    private String fileContentTypes;

    private LenderDocumentTypes lenderDocumentType;

    private Date upLoadedOn;

}
