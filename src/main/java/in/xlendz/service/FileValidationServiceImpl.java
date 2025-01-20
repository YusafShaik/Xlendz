package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.FileUploadTypes;
import in.xlendz.entity.XlendzUser;
import in.xlendz.exception.InvalidFileException;
import in.xlendz.repo.FileUploadRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidationServiceImpl implements FileValidationService{
    private final FileUploadRepo fileUploadRepository;

    public FileValidationServiceImpl(FileUploadRepo fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }

    @Override
    public void validateFile(MultipartFile file, FileUploadTypes fileUploadType, XlendzUser xlendzUser) {
        try{
            boolean isDuplicate = fileUploadRepository.existsByXlendzUserAndDocTypeAndFileName(
                    xlendzUser, fileUploadType, file.getOriginalFilename()
            );
            if (isDuplicate) {
                throw new InvalidFileException(ExceptionConstants.DUPLICATE_FILE);
            }
        }catch (InvalidFileException  e){
            throw new InvalidFileException(e.getMessage());
        }catch (Exception e){
            throw new InvalidFileException(ExceptionConstants.INVALID_FILE);
        }


    }
}
