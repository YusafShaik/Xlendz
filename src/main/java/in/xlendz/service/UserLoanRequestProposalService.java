package in.xlendz.service;

import in.xlendz.constants.MethodResponse;

public interface UserLoanRequestProposalService {
    MethodResponse userLoanRequestProposalService(Long userId, Long proposalId);
}
