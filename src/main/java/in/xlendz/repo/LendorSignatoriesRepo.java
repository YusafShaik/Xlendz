package in.xlendz.repo;

import in.xlendz.entity.LenderSignatories;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LendorSignatoriesRepo  extends JpaRepository<LenderSignatories,Long> {
    Optional<List<LenderSignatories>> findAllByXlendzLender(XlendzLender lender);
}
