package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.XlendzUser;
import in.xlendz.entity.XlendzUserIdentityDetails;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.UserIdentityDetailsRepo;
import in.xlendz.requests.UserIdentityDetailsRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserIdentityDetailServiceImpl implements UserIdentityDetailService{
    private static final Logger LOGGER= LoggerFactory.getLogger(UserIdentityDetailServiceImpl.class);
    private final UserDetailsService userDetailsService;
    private final UserIdentityDetailsRepo xlendzUserIdentityDetailsRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public UserIdentityDetailServiceImpl(UserDetailsService userDetailsService, UserIdentityDetailsRepo xlendzUserIdentityDetailsRepo, ModelMapper modelMapper) {
        this.userDetailsService = userDetailsService;
        this.xlendzUserIdentityDetailsRepo = xlendzUserIdentityDetailsRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public MethodResponse saveUserIdentityDetails(UserIdentityDetailsRequest userIdentityDetailsRequest) {
        try {
            LOGGER.info("Saving User Identity Details");
            XlendzUserIdentityDetails identityDetail = modelMapper.map(userIdentityDetailsRequest,
                    XlendzUserIdentityDetails.class);
            XlendzUser user = userDetailsService.getUserByUserId(userIdentityDetailsRequest.getXlendzUserId());
            identityDetail.setXlendzUser(user);
            xlendzUserIdentityDetailsRepo.save(identityDetail);
            LOGGER.info("User Identity Details are saved successfully");
           return MethodResponse.SUCCESS;
        } catch (DataUpdationException e) {
           throw new DataUpdationException("Something went wrong while saving user identity details please try again",e);
        }

    }
}
