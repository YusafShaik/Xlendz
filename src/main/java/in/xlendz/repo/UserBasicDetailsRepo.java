package in.xlendz.repo;

import in.xlendz.constants.KycStatus;
import in.xlendz.entity.XlendzUserBasicDetails;
import in.xlendz.entity.XlendzUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBasicDetailsRepo extends JpaRepository<XlendzUserBasicDetails,Long> {

    XlendzUserBasicDetails findByXlendzUserId(XlendzUser user);

    List<XlendzUserBasicDetails> findByXlendzUserId_KycStatus(KycStatus kycStatus);
}
