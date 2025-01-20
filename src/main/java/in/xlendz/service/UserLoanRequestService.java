package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.UserLoanRequestDto;

public interface UserLoanRequestService {
    MethodResponse createLoanRequest(UserLoanRequestDto userLoanRequestDto);

    MethodResponse updateLoanRequest(Long userId, Long loanRequestId);
}
