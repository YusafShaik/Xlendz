package in.xlendz.repo;

import in.xlendz.constants.KycStatus;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserDocs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<XlendzUser, Long>{


     Optional<XlendzUser> findByXlendzUserId(Long xlendzUserId) ;
    Boolean existsByEmail(String email);

    List<XlendzUserDocs> findByXlendzUserId(XlendzUser user);

    List<XlendzUser> findByKycStatus(KycStatus kycStatus);

    XlendzUser findByEmail(String xlendzUserId);
}
