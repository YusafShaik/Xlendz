package in.xlendz.service;

import in.xlendz.constants.FileUploadTypes;
import in.xlendz.entity.XlendzUser;
import org.springframework.web.multipart.MultipartFile;

public interface FileValidationService {
    void validateFile(MultipartFile file, FileUploadTypes fileUploadType, XlendzUser xlendzLender);
}
