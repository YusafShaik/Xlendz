package in.xlendz.repo;


import in.xlendz.constants.UserDetailsType;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserKycReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKycReviewRepo extends JpaRepository<XlendzUserKycReview, Long> {
    List<XlendzUserKycReview> findByXlendzUser_XlendzUserIdAndIsLatest(Long userId, boolean isLatest);

    List<XlendzUserKycReview> findByXlendzUserAndIsLatestAndDetailsType(XlendzUser xlendzUser, boolean b,
                                                                        UserDetailsType type);
    List<XlendzUserKycReview> findByXlendzUser(XlendzUser xlendzUser);
}
