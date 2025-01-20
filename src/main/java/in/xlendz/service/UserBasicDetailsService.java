package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.XlendzUserDetailRequest;
import in.xlendz.responses.UserDetailSaveResponse;

public interface UserBasicDetailsService {
    public MethodResponse saveUserBasicDetails(XlendzUserDetailRequest xlendzUserDetailRequest);
}
