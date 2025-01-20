package in.xlendz.repo;

import in.xlendz.entity.LenderProfile;
import in.xlendz.entity.LenderTaxInfo;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LenderTaxInfoRepo extends JpaRepository<LenderTaxInfo,Integer> {
    Optional<LenderTaxInfo> findByLender(XlendzLender lender);
}
