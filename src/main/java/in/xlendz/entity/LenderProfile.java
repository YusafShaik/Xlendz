package in.xlendz.entity;

import in.xlendz.constants.LoanTypesOffered;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "lender_profile_details")
@Builder
@NoArgsConstructor
public class LenderProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lenderProfileId;
    @OneToOne
    private XlendzLender lender;
    private String profileDocName;
    @Lob
    private byte[] profileDocData;
    private String profileDocContentType;

    @ElementCollection(targetClass = LoanTypesOffered.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "lender_profile_loan_types", joinColumns = @JoinColumn(name = "lender_profile_id"))
    @Column(name = "loan_type")
    List<LoanTypesOffered> loanTypesOffered;
}
