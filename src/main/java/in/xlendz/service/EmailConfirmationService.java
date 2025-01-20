package in.xlendz.service;


import in.xlendz.constants.MethodResponse;

public interface EmailConfirmationService {
    MethodResponse confirmEmail(String token);
}
