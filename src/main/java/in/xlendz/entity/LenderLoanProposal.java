package in.xlendz.entity;

import in.xlendz.constants.ProposalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lender_loan_proposal")
@Builder
public class LenderLoanProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanProposalId;

    @ManyToOne
    @JoinColumn(name = "lender_id", nullable = false)
    private XlendzLender xlendzLender;

    private Long loanRequestId;

    private double rateOfInterest;

    private double processingFees;

    private int tenure;

    private double loanAmount;

    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
}
