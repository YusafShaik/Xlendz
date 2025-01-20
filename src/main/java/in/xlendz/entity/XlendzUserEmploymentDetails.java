package in.xlendz.entity;
import in.xlendz.constants.EmploymentTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="xlendz_user_employment_details")
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserEmploymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn
    private XlendzUser xlendzUser;
    private String employerName;
    private String designation;
    private String annualPackage;
    private String inHandSalaryMonthly;
    @Enumerated(EnumType.STRING)
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
