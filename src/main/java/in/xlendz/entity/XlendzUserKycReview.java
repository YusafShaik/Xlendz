package in.xlendz.entity;

import in.xlendz.constants.DetailsStatus;
import in.xlendz.constants.UserDetailsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "xlendz_user_kyc_review")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class XlendzUserKycReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    @JoinColumn
    private XlendzUser xlendzUser;

    @Enumerated(EnumType.STRING)
    private UserDetailsType detailsType;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private DetailsStatus detailsStatus;

    private boolean isLatest;

}
