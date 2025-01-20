package in.xlendz.entity;

import in.xlendz.constants.KycStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@Table
(name="xlendz_lender")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class XlendzLender {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xlendz_lender_seq")
    @SequenceGenerator(name = "xlendz_lender_seq", sequenceName = "xlendz_lender_seq", allocationSize = 1)
    private Long lenderId;

    @Column(unique = true)
    private String email;

    private String password;

    @CreationTimestamp
    private Date userCreatedOn;

    private Boolean isVerified;

    private KycStatus kycStatus;
}
