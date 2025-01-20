package in.xlendz.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserAddressResponse {
    private String houseNumber;
    private String streetName;
    private String city;
    private String state;
    private int pinCode;
    private String addressTye;
    private char latestAddress;
}
