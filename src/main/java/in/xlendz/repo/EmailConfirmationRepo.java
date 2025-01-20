package in.xlendz.repo;

import in.xlendz.entity.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationRepo extends JpaRepository<EmailConfirmation, Long>{
    EmailConfirmation findByConfirmationToken(String token);
}
