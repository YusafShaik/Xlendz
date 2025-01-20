package in.xlendz.entity;

import in.xlendz.constants.LoanRequestStatus;
import in.xlendz.constants.LoanTypesOffered;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_loan_request")
@Builder
@Data
public class UserLoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanRequestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private XlendzUser xlendzUser;

    @Enumerated(EnumType.STRING)
    private LoanTypesOffered loanType;

    private Double loanAmount;

    @CreationTimestamp
    private Date createdOn;

    @Enumerated(EnumType.STRING)
    private LoanRequestStatus loanRequestStatus;
}
