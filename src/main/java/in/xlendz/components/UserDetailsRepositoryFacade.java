package in.xlendz.components;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.entity.*;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.repo.*;
import in.xlendz.util.EmailValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetailsRepositoryFacade {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserDetailsRepositoryFacade.class);
    private final UserRepo userRepo;
    private final UserBasicDetailsRepo userBasicDetailsRepo;
    private final UserIdentityDetailsRepo userIdentityDetailsRepo;
    private final UserEmploymentDetailsRepo userEmploymentDetailsRepo;
    private final FileUploadRepo fileUploadRepo;


    public UserDetailsRepositoryFacade(UserRepo userRepo, UserBasicDetailsRepo userBasicDetailsRepo,
                                       UserIdentityDetailsRepo userIdentityDetailsRepo,
                                       UserEmploymentDetailsRepo employmentDetailsRepo,
                                       FileUploadRepo fileUploadRepository) {
        this.userRepo = userRepo;
        this.userBasicDetailsRepo = userBasicDetailsRepo;
        this.userIdentityDetailsRepo = userIdentityDetailsRepo;
        this.userEmploymentDetailsRepo = employmentDetailsRepo;
        this.fileUploadRepo = fileUploadRepository;
    }
    public XlendzUser getByXlendzUserIdOrEmail(String xlendzUserId) {
        LOGGER.info("Fetching user by userId or email: {}", xlendzUserId);
        if (EmailValidator.isValidEmail(xlendzUserId)) {
            LOGGER.info("Fetching user by email: {}", xlendzUserId);
            return userRepo.findByEmail(xlendzUserId);
        }
        LOGGER.info("Fetching user by userId: {}", xlendzUserId);
        Optional<XlendzUser> xlendzUser=userRepo.findByXlendzUserId(Long.valueOf(xlendzUserId));
        if(xlendzUser.isPresent()){
            return xlendzUser.get();
        }
        throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND + xlendzUserId);
    }

    public XlendzUserBasicDetails findUserBasicDetails(XlendzUser user) {
        LOGGER.info("Fetching user basic details by userId: {}", user.getXlendzUserId());
         return userBasicDetailsRepo.findByXlendzUserId(user);
    }

    public XlendzUserIdentityDetails findUserIdentityDetails(XlendzUser user) {
        LOGGER.info("Fetching user identity details by userId: {}", user.getXlendzUserId());
        return userIdentityDetailsRepo.findByXlendzUser(user);
    }

    public XlendzUserEmploymentDetails getUserEmploymentDetails(XlendzUser user) {
      LOGGER.info("Fetching user employment details by userId: {}", user.getXlendzUserId());
       return userEmploymentDetailsRepo.findByXlendzUser(user);
    }

    @Transactional
    public List<XlendzUserDocs> findUserDocs(XlendzUser user) {
        LOGGER.info("Fetching user docs by userId: {}", user.getXlendzUserId());
        return fileUploadRepo.findByXlendzUser(user);
    }

    public XlendzUser findUserById(Long userId) {
        LOGGER.info("Fetching user by userId: {}", userId);
        Optional<XlendzUser> user = userRepo.findByXlendzUserId(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND + userId);

    }
}
