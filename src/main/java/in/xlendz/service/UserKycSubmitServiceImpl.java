package in.xlendz.service;

import in.xlendz.components.UserDetailsRepositoryFacade;
import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.FileUploadTypes;
import in.xlendz.constants.KycStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.XlendzUser;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserKycSubmitServiceImpl implements UserKycSubmitService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserKycSubmitServiceImpl.class);

    private final UserDetailsRepositoryFacade  userDetailsRepositoryFacade;

    private final UserRepo userRepo;

    @Autowired
    public UserKycSubmitServiceImpl(UserDetailsRepositoryFacade userDetailsRepositoryFacade, UserRepo userRepo) {
        this.userDetailsRepositoryFacade = userDetailsRepositoryFacade;
        this.userRepo = userRepo;
    }

    @Override
    public MethodResponse submitUserProfile(String xlendzUserId) {
        try {
            LOGGER.info("Submitting user kyc details for userId: {}", xlendzUserId);
            XlendzUser user = userDetailsRepositoryFacade.findUserById(Long.parseLong(xlendzUserId));
            if (user == null) {
                throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND);
            }
            if (userDetailsRepositoryFacade.findUserBasicDetails(user) == null) {
                LOGGER.error("User basic details not found for userId: {}", xlendzUserId);
                return MethodResponse.PLEASE_UPDATE_ALL_DETAILS;
            }
            if (userDetailsRepositoryFacade.findUserIdentityDetails(user) == null) {
                LOGGER.error("User identity details not found for userId: {}", xlendzUserId);
                return MethodResponse.PLEASE_UPDATE_ALL_DETAILS;
            }
            if (userDetailsRepositoryFacade.getUserEmploymentDetails(user) == null) {
                LOGGER.error("User employment details not found for userId: {}", xlendzUserId);
                return MethodResponse.PLEASE_UPDATE_ALL_DETAILS;
            }
            if (userDetailsRepositoryFacade.findUserDocs(user).size()< FileUploadTypes.values().length) {
                LOGGER.error("User All documents are not found for userId: {}", xlendzUserId);
                return MethodResponse.PLEASE_UPDATE_ALL_DETAILS;
            }
            user.setKycStatus(KycStatus.PENDING);
            userRepo.save(user);
            LOGGER.info("User kyc details submitted successfully for userId: {}", xlendzUserId);
            return MethodResponse.KYC_SUBMITTED_SUCCESSFULLY;
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException(ExceptionConstants.ERROR_WHILE_FETCHING_USER_DETAILS, e);
        } catch (DataUpdationException e) {
            throw new DataUpdationException("Error while submitting user kyc details", e);
        }

    }
}
