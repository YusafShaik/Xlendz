package in.xlendz.service;

import in.xlendz.constants.KycStatus;
import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LenderKycCommentRequest;

public interface LenderKycUpdateService {
    MethodResponse submitLenderKyc(String lenderEmail);

    MethodResponse lenderKycComments(LenderKycCommentRequest lenderKycCommentRequest);

    MethodResponse lenderKycApprove(String lenderEmail);

    MethodResponse updateLenderKycStatus(String lenderEmail, KycStatus kycStatus);
}
