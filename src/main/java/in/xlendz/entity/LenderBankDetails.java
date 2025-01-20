package in.xlendz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name = "lender_bank_details")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LenderBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lenderBankDetailsId;
    @OneToOne
    private XlendzLender lender;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
}
