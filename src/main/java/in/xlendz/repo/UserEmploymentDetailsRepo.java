package in.xlendz.repo;

import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserEmploymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmploymentDetailsRepo extends JpaRepository<XlendzUserEmploymentDetails, Long> {
    XlendzUserEmploymentDetails findByXlendzUser(XlendzUser user);
}
