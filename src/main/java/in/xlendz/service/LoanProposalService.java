package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LoanProposalDTO;
import in.xlendz.requests.LoanProposalUpdateDTO;

public interface LoanProposalService {
    MethodResponse submitLoanProposal( LoanProposalDTO loanProposalDTO);

    MethodResponse updateLoanProposal(LoanProposalUpdateDTO loanProposalDTO);
}
