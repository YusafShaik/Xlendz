package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.service.EmailConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("confirm-email")
@RestController
public class EmailConfirmationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfirmationController.class);
    private final EmailConfirmationService emailConfirmationService;
    @Autowired
    public EmailConfirmationController(EmailConfirmationService emailConfirmationService) {
        this.emailConfirmationService = emailConfirmationService;
    }

    @GetMapping("/confirm")
    public ResponseEntity<MethodResponse> confirmEmail(@RequestParam String token) {
        LOGGER.info("Received request to confirm email with token: {}", token);
        return ResponseEntity.ok(emailConfirmationService.confirmEmail(token));
    }
}
