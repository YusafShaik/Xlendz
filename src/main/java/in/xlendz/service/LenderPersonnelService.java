package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.LenderKeyPersonsRequest;
import in.xlendz.requests.LenderSignatoriesRequest;

public interface LenderPersonnelService {
    MethodResponse saveLendorSignatories(LenderSignatoriesRequest lendersignatoriesRequest);

    MethodResponse saveKeyPersonnel(LenderKeyPersonsRequest lenderKeyPersonsRequest);
}
