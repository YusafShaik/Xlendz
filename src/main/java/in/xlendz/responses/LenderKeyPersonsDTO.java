package in.xlendz.responses;

import in.xlendz.constants.KeyPersonRoles;
import in.xlendz.entity.XlendzLender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LenderKeyPersonsDTO {

    private String keyPersonName;

    private String keyPersonPhone;

    private String keyPersonEmail;

    private KeyPersonRoles keyPersonRole;

    private String fileContentTypes;

    private String fileName;
}
