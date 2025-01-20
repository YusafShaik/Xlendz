package in.xlendz.repo;

import in.xlendz.constants.LenderDocumentTypes;
import in.xlendz.entity.LenderDocuments;
import in.xlendz.entity.XlendzLender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LenderDocsRepo extends JpaRepository<LenderDocuments,Long> {
   Optional<LenderDocuments> findByLenderDocumentTypeAndXlendzLender_Email( LenderDocumentTypes lenderDocumentTypes, String lenderEmail);

   Optional<List<LenderDocuments>> findAllByXlendzLender(XlendzLender lender);
}
