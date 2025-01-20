package in.xlendz.repo;

import in.xlendz.constants.KycStatus;
import in.xlendz.entity.LenderBasicDetails;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LenderBasicDetailsRepo extends JpaRepository<LenderBasicDetails,Integer > {

    Optional<LenderBasicDetails> findByXlendzLender(XlendzLender xlendzLender);
    List<LenderBasicDetails> findByXlendzLender_KycStatus(KycStatus kycStatus);

    @Query("SELECT b.companyName, b.xlendzLender.lenderId FROM LenderBasicDetails b")
    List<Object[]> findCompanyNamesAndLenderIds();
}
