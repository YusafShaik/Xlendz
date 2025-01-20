package in.xlendz.service;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.SignupMailUserType;
import in.xlendz.entity.EmailConfirmation;
import in.xlendz.entity.XlendzLender;
import in.xlendz.entity.XlendzUser;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.exception.UserNotFoundException;
import in.xlendz.repo.EmailConfirmationRepo;
import in.xlendz.repo.LenderRepo;
import in.xlendz.repo.UserRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService{

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EmailConfirmationServiceImpl.class);


    private final EmailConfirmationRepo emailConfirmationRepo;
    private final LenderRepo lenderRepo;
    private final UserRepo xlendzUserRepo;
    @Autowired
    public EmailConfirmationServiceImpl(EmailConfirmationRepo emailConfirmationRepo, LenderRepo lenderRepo,
                                        UserRepo xlendzUserRepo) {
        this.emailConfirmationRepo = emailConfirmationRepo;
        this.lenderRepo = lenderRepo;
        this.xlendzUserRepo = xlendzUserRepo;
    }

    @Override
    public MethodResponse confirmEmail(String token) {

        try {
            LOGGER.info("Confirming user/lender email with token: {}", token);
            EmailConfirmation emailConfirmation = emailConfirmationRepo.findByConfirmationToken(token);
            if (emailConfirmation != null && !emailConfirmation.isUsed()) {
                emailConfirmation.setUsed(true);
                emailConfirmationRepo.save(emailConfirmation);
              boolean  isLender=emailConfirmation.getSignupMailUserType().equals(SignupMailUserType.LENDER);
              if(isLender){
                  return updateLender(emailConfirmation.getEmail());
              }else{
                  return updateUser(emailConfirmation.getEmail());
              }
            }
           return MethodResponse.SUCCESS;
        }catch (DataRetrievalException e){
            throw new DataUpdationException(ExceptionConstants.VERIFICATION_EXCEPTION,e);
        }

    }
    private MethodResponse updateUser(String email) {
        try {
            LOGGER.info("Updating user verification status: {}", email);
            XlendzUser user= xlendzUserRepo.findByEmail(email);
            user.setIsVerified(true);
            xlendzUserRepo.save(user);
            return MethodResponse.SUCCESS;
        }catch (DataRetrievalException e){
            throw new DataUpdationException(ExceptionConstants.VERIFICATION_EXCEPTION,e);
        }
    }
    private MethodResponse updateLender(String email) {
        try {
            LOGGER.info("Updating lender verification status: {}", email);
            Optional<XlendzLender> lender= lenderRepo.findByEmail(email);
            if(lender.isPresent()){
                lender.get().setIsVerified(true);
                lenderRepo.save(lender.get());
                return MethodResponse.SUCCESS;
            }else{
                throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND);
            }
        }catch (DataRetrievalException e){
            throw new DataUpdationException(ExceptionConstants.VERIFICATION_EXCEPTION,e);
        }
    }
}
