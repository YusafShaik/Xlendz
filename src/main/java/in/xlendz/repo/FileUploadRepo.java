package in.xlendz.repo;

import in.xlendz.constants.FileUploadTypes;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserDocs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileUploadRepo extends JpaRepository<XlendzUserDocs, Long>{
    List<XlendzUserDocs> findByXlendzUser(XlendzUser xlendzUser);
    boolean existsByXlendzUserAndDocTypeAndFileName(XlendzUser xlendzUser, FileUploadTypes fileUploadType, String originalFilename);


}
