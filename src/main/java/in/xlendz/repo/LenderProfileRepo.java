package in.xlendz.repo;

import in.xlendz.entity.LenderProfile;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LenderProfileRepo extends JpaRepository<LenderProfile,Integer> {
    Optional<LenderProfile> findByLender(XlendzLender lender);
}
