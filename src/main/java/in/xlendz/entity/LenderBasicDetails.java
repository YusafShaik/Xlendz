package in.xlendz.entity;

import in.xlendz.constants.LenderBankingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "lender_basic_details")
public class LenderBasicDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lenderBasicDetailsId;

    @OneToOne
    @JoinColumn
    private XlendzLender xlendzLender;

    private String companyName;

    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    private LenderBankingType lenderBankingType;

    private Date dateOfInCorporation;

}
