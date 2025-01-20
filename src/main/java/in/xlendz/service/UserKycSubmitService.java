package in.xlendz.service;

import in.xlendz.constants.MethodResponse;

public interface UserKycSubmitService {
    MethodResponse submitUserProfile(String xlendzUserId);
}
