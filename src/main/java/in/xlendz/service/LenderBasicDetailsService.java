package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LenderBankDetailsRequest;
import in.xlendz.requests.LenderBasicDetailsRequest;

public interface LenderBasicDetailsService {
    MethodResponse saveLenderBasicDetails(LenderBasicDetailsRequest lenderBasicDetailsRequest);


}
