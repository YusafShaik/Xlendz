package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.XlendzUserDocsRequest;
import in.xlendz.responses.UserDetailSaveResponse;

public interface FileUploadService {
    MethodResponse saveUserFile(XlendzUserDocsRequest request);
}
