package in.xlendz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="xlendz_user_identity_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserIdentityDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identityDetailId;
    @OneToOne
    @JoinColumn(nullable = false)
    private XlendzUser xlendzUser;
    private String panNumber;
    private String aadharNumber;
}
