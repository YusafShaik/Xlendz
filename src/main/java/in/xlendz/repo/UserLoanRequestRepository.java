package in.xlendz.repo;

import in.xlendz.constants.LoanRequestStatus;
import in.xlendz.entity.UserLoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLoanRequestRepository extends JpaRepository<UserLoanRequest,Long> {
    Optional<UserLoanRequest> findByXlendzUser_XlendzUserIdAndLoanRequestId(Long userId, Long loanRequestId);

    Optional<UserLoanRequest> findByXlendzUser_XlendzUserIdAndLoanRequestStatus(Long userId, LoanRequestStatus loanRequestStatus);

    List<UserLoanRequest> findAllByLoanRequestStatus(LoanRequestStatus loanRequestStatus);
}
