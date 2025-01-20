package in.xlendz.repo;

import in.xlendz.entity.LenderBankDetails;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LenderBankDetailsRepo  extends JpaRepository<LenderBankDetails, Integer> {
    Optional<LenderBankDetails> findByLender(XlendzLender lender);
}
