package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LenderBankDetailsRequest;

public interface LenderBankDetailsService {

    MethodResponse saveBankDetails(LenderBankDetailsRequest lenderBankDetailsRequest);
}
