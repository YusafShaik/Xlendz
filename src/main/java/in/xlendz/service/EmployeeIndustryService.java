package in.xlendz.service;

import in.xlendz.constants.MethodResponse;
import in.xlendz.responses.EmployeeIndustryResponse;

import java.util.List;

public interface EmployeeIndustryService{
    MethodResponse saveIndustry(String industryName);

    List<EmployeeIndustryResponse> getSectors();
}
