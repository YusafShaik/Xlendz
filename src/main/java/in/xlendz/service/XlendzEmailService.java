package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.SignupMailUserType;
import in.xlendz.entity.XlendzLender;

public interface XlendzEmailService {

    MethodResponse sendConfirmationMail(String email, SignupMailUserType signupMailUserType);
}
