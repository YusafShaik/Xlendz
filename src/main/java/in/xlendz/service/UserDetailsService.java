package in.xlendz.service;

import in.xlendz.entity.XlendzUser;
import in.xlendz.responses.UserDetailsResponse;

public interface UserDetailsService {
    UserDetailsResponse getUserDetails(String xlendzUserId);

    XlendzUser getUserByUserId(Long xlendzUserId);

}
