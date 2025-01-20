package in.xlendz.responses;

import in.xlendz.constants.SignatoryRoles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderSignatoriesDTO {

    private String signatoryEmail;

    private String signatoryName;

    private String signatoryPhone;

    private SignatoryRoles signatoryRole;

}
