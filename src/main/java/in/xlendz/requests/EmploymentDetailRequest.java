package in.xlendz.requests;

import in.xlendz.constants.EmploymentTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDetailRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long xlendzUserId;

    @NotBlank(message = "Employer name is required")
    private String employerName;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotBlank(message = "Annual package is required")
    private String annualPackage;

    @NotBlank(message = "In-hand salary monthly is required")
    private String inHandSalaryMonthly;

    @NotNull(message = "Employment type is required")
    private EmploymentTypes employmentType;

    @NotBlank(message = "Industry is required")
    private String industry;

    @NotNull(message = "Work experience is required")
    @Positive(message = "Work experience must be a positive number")
    private double workExperience;

    @NotBlank(message = "Office name is required")
    private String officeName;

    @NotBlank(message = "Office number is required")
    private String officeNumber;

    private String streetName;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zipcode is Required")
    private String zipCode;

    @NotBlank(message = "Office email is required")
    private String officeEmail;
}
