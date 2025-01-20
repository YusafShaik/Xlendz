package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserEmploymentDetails;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.repo.UserEmploymentDetailsRepo;
import in.xlendz.requests.EmploymentDetailRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEmploymentDetailsServiceImpl implements UserEmploymentDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEmploymentDetailsServiceImpl.class);
    private final UserEmploymentDetailsRepo xlendzUserEmploymentDetailsRepo;
    private final ModelMapper modelMapper;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserEmploymentDetailsServiceImpl(UserEmploymentDetailsRepo xlendzUserEmploymentDetailsRepo,
                                            ModelMapper modelMapper,
                                            UserDetailsService userDetailsService) {
        this.xlendzUserEmploymentDetailsRepo = xlendzUserEmploymentDetailsRepo;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public MethodResponse saveUserEmploymentDetails(EmploymentDetailRequest employmentDetailRequest) {
        try {
            LOGGER.info("Saving User Employment Details");
            XlendzUserEmploymentDetails employmentDetails = modelMapper.map(employmentDetailRequest,
                    XlendzUserEmploymentDetails.class);
            XlendzUser user = userDetailsService.getUserByUserId(employmentDetailRequest.getXlendzUserId());
            if(user==null){
                throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND +employmentDetailRequest.getXlendzUserId());
            }
            employmentDetails.setXlendzUser(user);
            xlendzUserEmploymentDetailsRepo.save(employmentDetails);
            LOGGER.info("User Employment Details are saved successfully");
            return MethodResponse.SUCCESS;
        } catch (DataUpdationException e) {
            throw new DataUpdationException("Something went wrong while saving user employment details", e);
        }
    }

}
