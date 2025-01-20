package in.xlendz.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressRequest {

    @NotBlank(message = "House number is required")
    private String houseNumber;
    private String streetName;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
   @NotBlank(message = "PinCode is required")
    private int pinCode;
    @NotBlank(message = "Address type is required")
    private String addressType;
    @NotBlank(message = "Confirm latest address or not")
    private char latestAddress;
}
