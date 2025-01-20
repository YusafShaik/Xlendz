package in.xlendz.util;

import in.xlendz.constants.ExceptionConstants;
import in.xlendz.exception.InvalidFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FIleValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FIleValidator.class);
    private FIleValidator(){}

    public static void emptyFileCheck(MultipartFile file){
        LOGGER.info("Validating file {}",file.getOriginalFilename());
        if(file.isEmpty()){
            throw new InvalidFileException(ExceptionConstants.INVALID_FILE);
        }
    }

}
