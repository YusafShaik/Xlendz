package in.xlendz.controller;

import in.xlendz.constants.MethodResponse;
import in.xlendz.requests.XlendzUserDocsRequest;
import in.xlendz.responses.UserDetailSaveResponse;
import in.xlendz.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
    private final FileUploadService fileUploadservice;
    @Autowired
    public FileUploadController(FileUploadService fileUploadservice) {
        this.fileUploadservice = fileUploadservice;
    }

    @PostMapping("/docs")
    public ResponseEntity<MethodResponse> uploadUserFiles(@ModelAttribute XlendzUserDocsRequest request) {
       LOGGER.info("Request received for uploading user files");
       return ResponseEntity.ok(fileUploadservice.saveUserFile(request));
    }
}
