package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LenderSignUpRequest;

public interface LenderSignUpService {
    MethodResponse lenderSignUp(LenderSignUpRequest lenderSignUpRequest);
}
