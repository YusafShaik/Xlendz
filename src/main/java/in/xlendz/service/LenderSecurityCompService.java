package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.SecurityComplianceRequest;

public interface LenderSecurityCompService {
    MethodResponse saveSecurityCompliance(SecurityComplianceRequest securityComplianceRequest);

}
