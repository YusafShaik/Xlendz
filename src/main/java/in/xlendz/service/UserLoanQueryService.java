package in.xlendz.service;

import in.xlendz.responses.UserLoanRequestProposalsDTO;
import in.xlendz.responses.UserLoanRequestResponse;
import java.util.List;

public interface UserLoanQueryService {
    UserLoanRequestProposalsDTO getLoanProposals(Long userId);
    List<UserLoanRequestResponse> getAllLoanRequests();
}
