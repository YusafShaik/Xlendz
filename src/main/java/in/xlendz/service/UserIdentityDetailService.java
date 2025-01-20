package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.UserIdentityDetailsRequest;

public interface UserIdentityDetailService {
    public MethodResponse saveUserIdentityDetails(UserIdentityDetailsRequest userIdentityDetailsRequest);
}
