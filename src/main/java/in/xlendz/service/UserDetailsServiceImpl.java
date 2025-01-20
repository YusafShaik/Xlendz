package in.xlendz.service;

import in.xlendz.components.UserDetailsRepositoryFacade;
import in.xlendz.entity.*;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.exception.XlendzDataAccessException;
import in.xlendz.responses.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserDetailsRepositoryFacade  userDetailsRepositoryFacade;
    protected final  ModelMapper modelMapper;

    public UserDetailsServiceImpl(UserDetailsRepositoryFacade userDetailsRepositoryFacade, ModelMapper modelMapper) {
        this.userDetailsRepositoryFacade = userDetailsRepositoryFacade;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDetailsResponse getUserDetails(String xlendzUserId) {
        try {
            XlendzUser user = userDetailsRepositoryFacade.getByXlendzUserIdOrEmail(xlendzUserId);
            if (user == null) {
                throw new UserNotFoundException("User not found with User Id : "+xlendzUserId);
            }
            UserDetailsResponse.UserDetailsResponseBuilder responseBuilder = UserDetailsResponse.builder()
                    .isVerified(user.getIsVerified())
                    .userId(user.getXlendzUserId())
                    .kycStatus(user.getKycStatus());

            XlendzUserBasicDetails basicDetails = userDetailsRepositoryFacade.findUserBasicDetails(user);
            if (basicDetails != null) {
                responseBuilder.userBasicDetails(modelMapper.map(basicDetails, XlendzUserBasicDetailsResponse.class));
            }

            XlendzUserIdentityDetails identityDetails = userDetailsRepositoryFacade.findUserIdentityDetails(user);
            if (identityDetails != null) {
                responseBuilder.userIdentityDetails(modelMapper.map(identityDetails,
                        UserIdentityDetailsResponse.class));
            }

            XlendzUserEmploymentDetails employmentDetails = userDetailsRepositoryFacade.getUserEmploymentDetails(user);
            if (employmentDetails != null) {
                responseBuilder.userEmploymentDetails(modelMapper.map(employmentDetails,
                        UserEmploymentDetailsResponse.class));
            }

            List<XlendzUserDocs> userDocs = userDetailsRepositoryFacade.findUserDocs(user);
            if (userDocs != null && !userDocs.isEmpty()) {
                List<XlendzUserDocsResponse> docsResponses = userDocs.stream()
                        .map(doc -> modelMapper.map(doc, XlendzUserDocsResponse.class))
                        .toList();
                responseBuilder.userDocs(docsResponses);
            }
            return responseBuilder.build();
        } catch (DataRetrievalException e) {
            throw new XlendzDataAccessException("Error while fetching user details", e);
        }

    }

    @Override
    public XlendzUser getUserByUserId(Long xlendzUserId) {
        return userDetailsRepositoryFacade.findUserById(xlendzUserId);
    }
}
