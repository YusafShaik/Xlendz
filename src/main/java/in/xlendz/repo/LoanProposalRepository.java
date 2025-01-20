package in.xlendz.repo;

import in.xlendz.constants.ProposalStatus;
import in.xlendz.entity.LenderLoanProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanProposalRepository extends JpaRepository<LenderLoanProposal, Long> {
    Optional<LenderLoanProposal> findByLoanProposalId(long loanProposalId);

    List<LenderLoanProposal> findByLoanRequestId(Long loanRequestId);
    @Modifying
    @Query("UPDATE LenderLoanProposal p SET p.proposalStatus = :status " +
            "WHERE p.loanRequestId = :requestId AND p.loanProposalId != :excludeProposalId")
    void updateProposalStatusByLoanRequestId(ProposalStatus status, Long requestId, Long excludeProposalId);
}
