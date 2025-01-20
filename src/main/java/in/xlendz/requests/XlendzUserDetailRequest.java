package in.xlendz.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserDetailRequest {

    @NotBlank(message = "User ID is required")
    private Long xlendzUserId;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Marital status is required")
    private char maritalStatus;
    @NotBlank(message = "Please select your gender")
    private char sex;
    @NotBlank(message="Father name is required")
    private String fatherName;

    private List<UserAddressRequest> address;
}
