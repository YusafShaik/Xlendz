package in.xlendz.repo;

import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LenderRepo  extends JpaRepository<XlendzLender, Long> {
    Optional<XlendzLender> findByEmail(String lenderEmail);
}
