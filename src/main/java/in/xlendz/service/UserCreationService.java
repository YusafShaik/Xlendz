package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.CreateXlendzUserRequest;
import in.xlendz.responses.UserDetailsFeedbackResponse;
import in.xlendz.responses.UserDetailsResponse;

public interface UserCreationService {
    MethodResponse createXlendzUser(CreateXlendzUserRequest userRequest);

}
