package in.xlendz.entity;

import in.xlendz.constants.SignupMailUserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="xlendz_email_confirmation_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tokenId;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private boolean isUsed;

    private String email;

    @Enumerated(EnumType.ORDINAL)
    private SignupMailUserType signupMailUserType;
}
