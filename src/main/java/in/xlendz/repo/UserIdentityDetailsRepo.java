package in.xlendz.repo;

import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserIdentityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIdentityDetailsRepo extends JpaRepository<XlendzUserIdentityDetails, Long> {
    XlendzUserIdentityDetails findByXlendzUser(XlendzUser user);
}
