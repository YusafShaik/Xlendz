package in.xlendz.repo;

import in.xlendz.entity.LenderKycReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LenderKycReviewRepo extends JpaRepository<LenderKycReview, Integer> {
    List<LenderKycReview> findByXlendzLender_LenderIdAndIsLatest(Long lenderId, boolean latest);
}
