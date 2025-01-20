package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.constants.SignupMailUserType;
import in.xlendz.entity.EmailConfirmation;
import in.xlendz.exception.EmailException;
import in.xlendz.repo.EmailConfirmationRepo;
import in.xlendz.requests.EmailDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class XlendzEmailServiceImpl implements XlendzEmailService {

    private static final Logger LOGGER= LoggerFactory.getLogger(XlendzEmailServiceImpl.class);
    @Value("${xlendz.confirmation.url}")
    private String confirmationUrl;
    @Value("${spring.mail.username}")
    private String fromEmail;
   private final JavaMailSender javaMailSender;
   private final EmailConfirmationRepo emailConfirmationRepo;

   @Autowired
    public XlendzEmailServiceImpl(JavaMailSender javaMailSender, EmailConfirmationRepo emailConfirmationRepo) {
       this.javaMailSender = javaMailSender;
       this.emailConfirmationRepo = emailConfirmationRepo;
   }

    @Override
    public MethodResponse sendConfirmationMail(String email, SignupMailUserType signupMailUserType) {
        try {
            LOGGER.info("Sending confirmation email to: {}", email);
            String emailToken = String.valueOf(UUID.randomUUID());
            emailConfirmationRepo.save( buildEmailConfirmObj(email, signupMailUserType, emailToken));
            LOGGER.info("Email confirmation token saved for email: {}", email);
            return sendEmail(buildEmailDetails(email, emailToken));
        } catch (EmailException e) {
            LOGGER.error("Error sending email: {}", e.getMessage());
            return MethodResponse.FAILURE;
        }
    }

    private static EmailConfirmation buildEmailConfirmObj(String email, SignupMailUserType signupMailUserType,
                                                          String emailToken) {
        return EmailConfirmation.builder()
                .email(email)
                .confirmationToken(emailToken)
                .isUsed(false)
                .signupMailUserType(signupMailUserType)
                .build();
    }

    private EmailDetails buildEmailDetails(String email, String token) {
        String baseConfirmationUrl = confirmationUrl + "/confirm-email/confirm?token=" + token;
        return new EmailDetails(email, "Xlendz Email Confirmation",
                "To confirm your email, please click the following link: " + baseConfirmationUrl,fromEmail );
    }

    private MethodResponse sendEmail(EmailDetails emailDetails) {
        try {
            LOGGER.info("Sending email to: {}", emailDetails.getTo());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDetails.getTo());
            message.setSubject(emailDetails.getSubject());
            message.setText(emailDetails.getText());
            message.setFrom(emailDetails.getFrom());
            javaMailSender.send(message);
            LOGGER.info("Email sent successfully to: {}", emailDetails.getTo());
            return MethodResponse.SUCCESS;
        } catch (EmailException e) {
            LOGGER.error("Error sending email: {}", e.getMessage());
            return MethodResponse.FAILURE;
        }

    }
}
