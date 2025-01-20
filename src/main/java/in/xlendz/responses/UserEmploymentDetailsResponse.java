package in.xlendz.responses;

import in.xlendz.constants.EmploymentTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmploymentDetailsResponse {
    private String employerName;
    private String designation;
    private String annualPackage;
    private String inHandSalaryMonthly;
    private EmploymentTypes employmentType;
    private String industry;
    private double workExperience;
    private String officeName;
    private String officeNumber;
    private String streetName;
    private String city;
    private String state;
    private String zipCode;
    private String officeEmail;
}
