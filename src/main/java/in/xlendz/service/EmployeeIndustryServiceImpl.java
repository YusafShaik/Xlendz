package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.entity.EmployeeIndustry;
import in.xlendz.exception.DataRetrievalException;
import in.xlendz.exception.DataUpdationException;
import in.xlendz.repo.EmployeeIndustryRepo;
import in.xlendz.responses.EmployeeIndustryResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeIndustryServiceImpl implements EmployeeIndustryService{

    private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeIndustryServiceImpl.class);
    private final EmployeeIndustryRepo employeeIndustryRepo;
    private final ModelMapper modelMapper;

    public EmployeeIndustryServiceImpl(EmployeeIndustryRepo employeeIndustryRepo, ModelMapper modelMapper) {
        this.employeeIndustryRepo = employeeIndustryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public MethodResponse saveIndustry(String industryName) {
        try {
            LOGGER.info("Received request to save industry: {}", industryName);
            employeeIndustryRepo.save(EmployeeIndustry.builder().industryName(industryName).build());
            return MethodResponse.SUCCESS;
        }catch (DataUpdationException e){
           throw new DataUpdationException(e.getMessage());
        }
    }

    @Override
    public List<EmployeeIndustryResponse> getSectors() {
        try{
            LOGGER.info("getting sectors");
            List<EmployeeIndustry> industries = employeeIndustryRepo.findAll();
            LOGGER.info("sectors : {}", industries.size());
            return industries.stream().
                    map(industry -> modelMapper.map(industry, EmployeeIndustryResponse.class)).toList();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException(e.getMessage());
        }catch (Exception e){
            LOGGER.error("Error occurred while fetching industries: {}", e.getMessage());
            throw new DataRetrievalException("Error occurred while fetching industries");
        }

    }
}
