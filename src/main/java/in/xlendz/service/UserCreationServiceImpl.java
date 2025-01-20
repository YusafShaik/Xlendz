package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.SignupMailUserType;
import in.xlendz.entity.*;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.*;
import in.xlendz.requests.CreateXlendzUserRequest;
import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCreationServiceImpl implements UserCreationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreationServiceImpl.class);
    private final UserRepo xlendzUserRepo;
    private final ModelMapper modelMapper;
    private final XlendzEmailService xlendzEmailService;

    @Autowired
    public UserCreationServiceImpl(UserRepo xlendzUserRepo, UserBasicDetailsRepo
            xlendzUserBasicDetailsRepo, UserIdentityDetailsRepo xlendzUserIdentityDetailsRepo,
                                   UserEmploymentDetailsRepo xlendzUserEmploymentDetailsRepo,
                                   UserKycReviewRepo xlendzUserKycReviewRepo,
                                   ModelMapper modelMapper, FileUploadRepo fileUploadRepository,
                                   XlendzEmailService xlendzEmailService) {
        this.xlendzUserRepo = xlendzUserRepo;
        this.modelMapper = modelMapper;
        this.xlendzEmailService = xlendzEmailService;
    }

    @Override
    public MethodResponse createXlendzUser(CreateXlendzUserRequest userRequest) {
        try {
            LOGGER.info("Starting XlendzUser Creation for : {}", userRequest.getEmail());
            Boolean isUserExist = xlendzUserRepo.existsByEmail(userRequest.getEmail());
            if (Boolean.TRUE.equals(isUserExist)) {
                LOGGER.info("User already exists with email: {}", userRequest.getEmail());
                return MethodResponse.USER_EXISTS;
            }
            XlendzUser user = modelMapper.map(userRequest, XlendzUser.class);
            user.setIsVerified(false);
            xlendzUserRepo.save(user);
            MethodResponse emailSentStatus = xlendzEmailService.sendConfirmationMail(user.getEmail(), SignupMailUserType.USER);
            LOGGER.info("Email sent status: {}", emailSentStatus);
            return MethodResponse.SUCCESS;
        } catch (DataUpdationException e) {
            throw new DataUpdationException("Error while creating user", e);
        }

    }


}
