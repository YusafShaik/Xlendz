package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserDocs;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.InvalidFileException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.repo.FileUploadRepo;
import in.xlendz.repo.UserRepo;
import in.xlendz.requests.XlendzUserDocsRequest;
import in.xlendz.responses.UserDetailSaveResponse;
import in.xlendz.util.FIleValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    private final UserRepo xlendzUserRepo;
    private final FileUploadRepo fileUploadRepository;

    private final FileValidationService fileValidationService;

    @Autowired
    public FileUploadServiceImpl(UserRepo xlendzUserRepo, FileUploadRepo fileUploadRepository,
                                 FileValidationService fileValidationService) {
        this.xlendzUserRepo = xlendzUserRepo;
        this.fileUploadRepository = fileUploadRepository;
        this.fileValidationService = fileValidationService;
    }

    @Override
    @Transactional
    public MethodResponse saveUserFile(XlendzUserDocsRequest request) {
        try {
            LOGGER.info("Request received for saving user file");
            Optional<XlendzUser> xlendzUser =
                    xlendzUserRepo.findById(request.getXlendzUserId());
            if (xlendzUser.isEmpty()) {
                LOGGER.error("User not found for id: {}", request.getXlendzUserId());
                throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND + request.getXlendzUserId().toString());
            }
            FIleValidator.emptyFileCheck(request.getFile());
            fileValidationService.validateFile(request.getFile(), request.getFileUploadType(), xlendzUser.get());
            XlendzUserDocs xlendzUserDocs = buildUserDocObj(request, xlendzUser.get());
            fileUploadRepository.save(xlendzUserDocs);
            LOGGER.info("User file saved successfully");
            return MethodResponse.SUCCESS;
        } catch ( InvalidFileException e) {
           throw new InvalidFileException(e.getMessage());
        }catch (DataRetrievalException | UserNotFoundException | IOException e){
            throw new XlendzDataAccessException(e.getMessage());
        }

    }

    private static XlendzUserDocs buildUserDocObj(XlendzUserDocsRequest request, XlendzUser xlendzUser) throws IOException {
        return XlendzUserDocs.builder()
                .xlendzUser(xlendzUser)
                .fileName(request.getFile().getOriginalFilename())
                .docType(request.getFileUploadType())
                .fileData(request.getFile().getBytes())
                .fileContentType(request.getFile().getContentType())
                .build();
    }

}
