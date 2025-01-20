package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.*;

public interface LenderDocumentsService {
    MethodResponse saveLenderRegistrationDocs(LenderFilesUploadRequest lenderFilesUploadRequest);
    MethodResponse saveCompanyProfile(CompanyProfileRequest companyProfileRequest);
    MethodResponse saveLenderTaxInfo(LenderTaxInfoRequest lenderTaxInfoRequest);








}
