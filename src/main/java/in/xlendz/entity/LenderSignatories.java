package in.xlendz.entity;

import in.xlendz.constants.SignatoryRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lender_signatories")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LenderSignatories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long signatoryId;

    @ManyToOne
    @JoinColumn
    private XlendzLender xlendzLender;

    @Column(unique = true)
    private String signatoryEmail;

    private String signatoryName;

    @Column(unique = true)
    private String signatoryPhone;

    @Enumerated(EnumType.STRING)
    private SignatoryRoles signatoryRole;

}
