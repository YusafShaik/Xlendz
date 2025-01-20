package in.xlendz.repo;

import in.xlendz.entity.LenderSecurityComplianceDocs;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LenderSecurityRepo extends JpaRepository<LenderSecurityComplianceDocs, Integer>{
    Optional<List<LenderSecurityComplianceDocs>> findAllByLender(XlendzLender lender);
}
