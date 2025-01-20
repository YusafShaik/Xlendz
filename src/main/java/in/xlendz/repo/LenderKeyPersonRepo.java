package in.xlendz.repo;

import in.xlendz.entity.LenderKeyPersons;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LenderKeyPersonRepo extends JpaRepository<LenderKeyPersons,Long> {
    Optional<List<LenderKeyPersons>> findAllByXlendzLender(XlendzLender lender);
}
