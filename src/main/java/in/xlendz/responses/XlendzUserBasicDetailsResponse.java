package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserBasicDetailsResponse {

    private String firstName;
    private String lastName;
    private char maritalStatus;
    private char sex;
    private String fatherName;
    private List<XlendzUserAddressResponse> userAddresses;
}
