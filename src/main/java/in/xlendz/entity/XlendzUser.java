package in.xlendz.entity;


import in.xlendz.constants.KycStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name="xlendz_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XlendzUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xlendz_user_seq")
    @SequenceGenerator(name = "xlendz_user_seq", sequenceName = "xlendz_user_seq", allocationSize = 1)
    private Long xlendzUserId;

    @Column(unique = true)
    private String email;

    private String password;

    @CreationTimestamp
    private Date userCreated;

    private Boolean isVerified;

    private KycStatus kycStatus=KycStatus.DETAILS_PENDING;

}
