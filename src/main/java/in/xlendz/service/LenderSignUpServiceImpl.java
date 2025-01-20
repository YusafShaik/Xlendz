package in.xlendz.service;

import in.xlendz.constants.KycStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.SignupMailUserType;
import in.xlendz.entity.XlendzLender;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.LenderRepo;
import in.xlendz.requests.LenderSignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LenderSignUpServiceImpl implements LenderSignUpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderSignUpServiceImpl.class);
    private final LenderRepo lenderRepo;
    private final XlendzEmailService xlendzEmailService;

    @Autowired
    public LenderSignUpServiceImpl(LenderRepo lenderRepo, XlendzEmailService xlendzEmailService) {
        this.lenderRepo = lenderRepo;
        this.xlendzEmailService = xlendzEmailService;
    }

    @Override
    public MethodResponse lenderSignUp(LenderSignUpRequest lenderSignUpRequest) {
        try {
            LOGGER.info("Received request to sign up lender, lenderEmail={}", lenderSignUpRequest.getLenderEmail());
           Optional<XlendzLender> xlendzLender = lenderRepo.findByEmail(lenderSignUpRequest.getLenderEmail());
            if (xlendzLender.isPresent()) {
                LOGGER.info("Lender already exists, lenderEmail={}", lenderSignUpRequest.getLenderEmail());
                return MethodResponse.USER_EXISTS;
            } else {
                LOGGER.info("Lender does not exist, creating new lender, lenderEmail={}", lenderSignUpRequest.getLenderEmail());
                lenderRepo.save(buildUser(lenderSignUpRequest));
                LOGGER.info("Lender created successfully, lenderEmail={}", lenderSignUpRequest.getLenderEmail());
                MethodResponse mailSent = xlendzEmailService.sendConfirmationMail(lenderSignUpRequest.getLenderEmail(),
                        SignupMailUserType.LENDER);
                LOGGER.info("Lender sign up mail sent successfully, lenderEmail={}, mailSent={}",
                        lenderSignUpRequest.getLenderEmail(), mailSent);
                LOGGER.info("Lender sign up successful, lenderEmail={}", lenderSignUpRequest.getLenderEmail());
                return MethodResponse.SUCCESS;
            }
        } catch (DataUpdationException e) {
            throw new DataUpdationException("Error occurred while signing up lender", e);
        }catch (Exception e){
            LOGGER.error("Error occurred while signing up lender, lenderEmail={}, error={}", lenderSignUpRequest.getLenderEmail(), e.getMessage());
            return MethodResponse.FAILURE;
        }

    }

    private static XlendzLender buildUser(LenderSignUpRequest lenderSignUpRequest) {
        LOGGER.info("Building lender entity, lenderEmail={}", lenderSignUpRequest.getLenderEmail());
        return XlendzLender.builder()
                .email(lenderSignUpRequest.getLenderEmail())
                .password(lenderSignUpRequest.getPassword())
                .isVerified(false)
                .kycStatus(KycStatus.DETAILS_PENDING)
                .build();
    }
}
