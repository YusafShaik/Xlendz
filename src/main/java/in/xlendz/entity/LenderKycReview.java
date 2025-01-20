package in.xlendz.entity;

import in.xlendz.constants.DetailsStatus;
import in.xlendz.constants.LenderDetailsType;
import in.xlendz.constants.UserDetailsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lender_kyc_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LenderKycReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    @JoinColumn
    private XlendzLender xlendzLender;

    @Enumerated(EnumType.STRING)
    private LenderDetailsType detailsType;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private DetailsStatus detailsStatus;

    private boolean isLatest;
}
