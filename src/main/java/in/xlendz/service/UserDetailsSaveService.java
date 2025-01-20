package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.EmploymentDetailRequest;
import in.xlendz.requests.UserIdentityDetailsRequest;
import in.xlendz.requests.XlendzUserDetailRequest;

public interface UserDetailsSaveService {
    MethodResponse saveUserBasicDetails(XlendzUserDetailRequest xlendzUserDetailRequest);

    MethodResponse saveUserIdentityDetails(UserIdentityDetailsRequest userIdentityDocRequest);

    MethodResponse saveUserEmploymentDetails(EmploymentDetailRequest employmentDetailRequest);
}
