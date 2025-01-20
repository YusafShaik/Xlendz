package in.xlendz.requests;

import in.xlendz.util.ValidPassword;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LenderSignUpRequest {

    @Email
    private String lenderEmail;

    @ValidPassword
    private String password;

}
