package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.repo.*;
import in.xlendz.requests.EmploymentDetailRequest;
import in.xlendz.requests.UserIdentityDetailsRequest;
import in.xlendz.requests.XlendzUserDetailRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSaveServiceImpl implements UserDetailsSaveService {

    private final UserIdentityDetailService userIdentityDetailService;
    private final UserBasicDetailsService userBasicDetailsService;
    private final UserEmploymentDetailsService userEmploymentDetailsService;

    @Autowired
    public UserDetailsSaveServiceImpl(UserIdentityDetailService userIdentityDetailService, UserBasicDetailsService userBasicDetailsService,
                                      UserEmploymentDetailsService userEmploymentDetailsService) {
        this.userIdentityDetailService = userIdentityDetailService;
        this.userBasicDetailsService = userBasicDetailsService;
        this.userEmploymentDetailsService = userEmploymentDetailsService;
    }

    @Override
    public MethodResponse saveUserBasicDetails(XlendzUserDetailRequest xlendzUserDetailRequest) {
        return userBasicDetailsService.saveUserBasicDetails(xlendzUserDetailRequest);
    }

    @Override
    public MethodResponse saveUserIdentityDetails(UserIdentityDetailsRequest userIdentityDetailsRequest) {
       return userIdentityDetailService.saveUserIdentityDetails(userIdentityDetailsRequest);
    }

    @Override
    public MethodResponse saveUserEmploymentDetails(EmploymentDetailRequest employmentDetailRequest) {
        return userEmploymentDetailsService.saveUserEmploymentDetails(employmentDetailRequest);
    }
}
