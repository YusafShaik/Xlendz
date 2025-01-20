package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.EmploymentDetailRequest;

public interface UserEmploymentDetailsService {
    public MethodResponse saveUserEmploymentDetails(EmploymentDetailRequest employmentDetailRequest);
}
