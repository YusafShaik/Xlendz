package in.xlendz.requests;

import in.xlendz.util.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateXlendzUserRequest {

    @Email
    @NotBlank(message = "Email should not be null or empty")
    private String email;

    @ValidPassword
    @NotBlank(message = "Password should not be null or empty")
    private String password;

}
