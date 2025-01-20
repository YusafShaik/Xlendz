package in.xlendz.service;

import in.xlendz.responses.KycReviewListResponse;
import in.xlendz.responses.LenderKycDetailsComments;

import java.util.List;

public interface LenderKycQueryService {

    List<KycReviewListResponse> getLenderKycList();

    LenderKycDetailsComments getLenderKycRemarks(String lenderEmail);
}
